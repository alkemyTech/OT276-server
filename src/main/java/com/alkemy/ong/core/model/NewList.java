package com.alkemy.ong.core.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class NewList extends PageImpl<New> {

public NewList(List<Alkymer> content, Pageable pageable, long total) {
        super(content, pageable, total);
        }

}
