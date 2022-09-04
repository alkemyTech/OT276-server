package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.New;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import org.springframework.stereotype.Component;

@Component
public class NewControllerMapperImpl implements NewControllerMapper {

    @Override
    public New createNewRequestToNew(CreateNewRequest createNewRequest) {
        if ( createNewRequest == null ) {
            return null;
        }

        New news = new New();

        news.setName(createNewRequest.getName());
        news.setContent(createNewRequest.getContent());

        return news;
    }
}
