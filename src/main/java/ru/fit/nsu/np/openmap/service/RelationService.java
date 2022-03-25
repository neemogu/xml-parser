package ru.fit.nsu.np.openmap.service;

import org.springframework.stereotype.Service;
import ru.fit.nsu.np.openmap.dao.RelationEntity;
import ru.fit.nsu.np.openmap.repository.RelationRepository;

@Service
public class RelationService extends BaseService<RelationEntity, RelationRepository> {
    public RelationService(RelationRepository repository) {
        super(repository);
    }
}
