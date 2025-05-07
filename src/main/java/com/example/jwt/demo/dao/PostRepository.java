package com.example.jwt.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.demo.domain.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>{
  
} 
