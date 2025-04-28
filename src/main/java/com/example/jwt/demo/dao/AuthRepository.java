package com.example.jwt.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.demo.domain.entity.UserEntity;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long>{
}
