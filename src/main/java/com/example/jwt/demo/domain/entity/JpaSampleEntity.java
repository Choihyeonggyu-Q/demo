package com.example.jwt.demo.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity
@Table(name = "siat_jpa_sample_tbl")
@DynamicUpdate
@Getter
public class JpaSampleEntity {
    @Id
    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "user_passwd", length = 50, nullable = false)
    private String passwd;

    @Column(name = "user_name", length = 50, nullable = false)
    private String name;

    @Column(name = "user_point", length = 50)
    private Integer point;

    @Column(name = "user_token")
    private String refreshToken;
}
