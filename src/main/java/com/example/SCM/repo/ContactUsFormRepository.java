package com.example.SCM.repo;

import com.example.SCM.entity.ContactUsForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsFormRepository extends JpaRepository<ContactUsForm, Long> {

}
