package com.contactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactmanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
