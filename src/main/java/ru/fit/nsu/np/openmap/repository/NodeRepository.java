package ru.fit.nsu.np.openmap.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.fit.nsu.np.openmap.dao.NodeEntity;

import java.util.List;

@Repository
public interface NodeRepository extends EntityRepository<NodeEntity> {
    @Query(value = "SELECT n FROM NodeEntity n " +
            "WHERE FUNCTION('earth_distance', FUNCTION('ll_to_earth', n.lat, n.lon), FUNCTION('ll_to_earth', :lat, :lon)) < :radius " +
            "ORDER BY FUNCTION('earth_distance', FUNCTION('ll_to_earth', n.lat, n.lon), FUNCTION('ll_to_earth', :lat, :lon))")
    List<NodeEntity> getNodesInRadius(@Param("lat") double lat, @Param("lon") double lon, @Param("radius") double radius);
}
