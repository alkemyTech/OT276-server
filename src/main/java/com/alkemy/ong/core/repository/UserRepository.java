package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
