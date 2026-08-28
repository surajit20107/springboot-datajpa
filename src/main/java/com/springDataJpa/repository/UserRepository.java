package com.springDataJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springDataJpa.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {}
