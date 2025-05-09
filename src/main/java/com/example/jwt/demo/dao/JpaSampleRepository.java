package com.example.jwt.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jwt.demo.domain.dto.SampleResponseDTO;
import com.example.jwt.demo.domain.entity.JpaSampleEntity;

import jakarta.transaction.Transactional;

//JPQL - update, delete
@Repository
public interface JpaSampleRepository extends JpaRepository<JpaSampleEntity, String>{
    /* select * from table_name where user_id = ? and passwd = ? */
    public Optional<JpaSampleEntity>findByUserIdAndPasswd(String userId, String passwd);

    @Modifying
    @Query("UPDATE JpaSampleEntity U "
            +"SET U.passwd = :passwd, U.name = :name "
            +"WHERE U.userId = :userId")
    public void updateRow(  @Param("userId") String userId,
                            @Param("passwd") String passwd,
                            @Param("name") String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM JpaSampleEntity D "
            +"WHERE D.userId = :userId")
    public void deleteRow( @Param("userId") String userId);

    @Query("SELECT new com.example.jwt.demo.domain.dto.SampleResponseDTO(E.userId, E.passwd, E.name) FROM JpaSampleEntity E")
    public List<SampleResponseDTO> findByAll();
}
