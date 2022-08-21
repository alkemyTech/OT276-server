package com.alkemy.ong.core.repository;

import com.alkemy.ong.core.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository <Contact,Long>{
}
