package com.smartcontactmanager.repository;

import com.smartcontactmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from user where u.email=:email")
    public User getUserByUserName(@Param("email") String email);
}
