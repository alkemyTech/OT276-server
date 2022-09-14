package com.alkemy.ong.core.usecase;


import com.alkemy.ong.core.model.New;

public interface NewService {

    Long createEntity(New news, Long categoryId);
}
