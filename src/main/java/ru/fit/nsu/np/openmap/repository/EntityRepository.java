package ru.fit.nsu.np.openmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fit.nsu.np.openmap.dao.OsmPersistentEntity;

public interface EntityRepository<T extends OsmPersistentEntity> extends JpaRepository<T, Long> {}
