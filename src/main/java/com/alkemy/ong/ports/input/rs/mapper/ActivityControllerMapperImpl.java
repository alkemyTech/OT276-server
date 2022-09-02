package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Activity;
import com.alkemy.ong.ports.input.rs.request.CreateActivityRequest;

public class ActivityControllerMapperImpl{

    public Activity createActivityRequestToActivity(CreateActivityRequest activityRequest) {
        if ( activityRequest == null ) {
            return null;
        }

        Activity activity = new Activity();

        activity.setName(activityRequest.getName());
        activity.setContent(activityRequest.getContent());

        return activity;
    }
}
