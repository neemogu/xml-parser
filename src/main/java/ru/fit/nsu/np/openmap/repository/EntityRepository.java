package ru.fit.nsu.np.openmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fit.nsu.np.openmap.dao.PersistentEntity;

public interface EntityRepository<T extends PersistentEntity> extends JpaRepository<T, Long> {}
