package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Alkymer;
import com.alkemy.ong.core.model.AlkymerList;
import org.springframework.data.domain.PageRequest;

public interface AlkymerService {
    Long createEntity(Alkymer entity);

    void deleteById(Long id);

    Alkymer getByIdIfExists(Long id);

    AlkymerList getList(PageRequest pageRequest);

    void updateEntityIfExists(Long id, Alkymer entity);
}
