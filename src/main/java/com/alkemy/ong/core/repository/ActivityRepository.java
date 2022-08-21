package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Activity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long> {
}
