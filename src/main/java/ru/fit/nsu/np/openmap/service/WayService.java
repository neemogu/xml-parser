package ru.fit.nsu.np.openmap.service;

import org.springframework.stereotype.Service;
import ru.fit.nsu.np.openmap.dao.WayEntity;
import ru.fit.nsu.np.openmap.repository.WayRepository;

@Service
public class WayService extends BaseService<WayEntity, WayRepository> {
    public WayService(WayRepository repository) {
        super(repository);
    }
}
