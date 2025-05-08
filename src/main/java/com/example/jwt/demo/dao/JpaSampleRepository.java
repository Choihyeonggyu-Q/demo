package com.example.jwt.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.demo.domain.entity.JpaSampleEntity;

@Repository
public interface JpaSampleRepository extends JpaRepository<JpaSampleEntity, String>{
    public Optional<JpaSampleEntity>findByUserIdAndPasswd(String userId, String passwd);
}
