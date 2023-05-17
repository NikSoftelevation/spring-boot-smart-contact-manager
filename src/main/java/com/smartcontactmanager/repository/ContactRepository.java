package com.smartcontactmanager.repository;

import com.smartcontactmanager.domain.Contact;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    //pagination
    @Query("from Contact as c where c.user.id=:userId")
    public Page<Contact> findContactsByUser(@Param(("userId")) int userId, Pageable pageable);
}
