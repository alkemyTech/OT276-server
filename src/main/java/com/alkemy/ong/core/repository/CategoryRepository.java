package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
