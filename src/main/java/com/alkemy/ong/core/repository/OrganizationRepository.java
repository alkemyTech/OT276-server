package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
