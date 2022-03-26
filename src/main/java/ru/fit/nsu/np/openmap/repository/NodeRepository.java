package ru.fit.nsu.np.openmap.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.fit.nsu.np.openmap.dao.NodeEntity;

import java.util.List;

@Repository
public interface NodeRepository extends EntityRepository<NodeEntity> {
    @Query(value = "SELECT * FROM node WHERE earth_distance(ll_to_earth(node.lat, node.lon), ll_to_earth(:lat, :lon)) < :radius " +
            "ORDER BY earth_distance(ll_to_earth(node.lat, node.lon), ll_to_earth(:lat, :lon))",
            nativeQuery = true)
    List<NodeEntity> getNodesInRadius(@Param("lat") double lat, @Param("lon") double lon, @Param("radius") double radius);
}
