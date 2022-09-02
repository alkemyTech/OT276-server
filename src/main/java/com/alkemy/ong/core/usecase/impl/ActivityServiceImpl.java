package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.core.model.Activity;
import com.alkemy.ong.core.repository.ActivityRepository;
import com.alkemy.ong.core.usecase.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Transactional
    public Long createEntity(Activity activity) {
        return activityRepository.save(activity).getId();
    }
}
