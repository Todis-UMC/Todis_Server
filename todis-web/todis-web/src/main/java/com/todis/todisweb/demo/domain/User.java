package com.todis.todisweb.demo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Data
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name="User")
public class User {
    // @DynamicInsert 데이터가 존재하는(null이 아닌) 필드만으로 INSERT문을 동적으로 생성함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="profile_image_url")
    private String profileImageUrl;
    @Column(name="cody_image")
    private String codyImage;
    @Column(name="field")
    private String field;

    @Column(name="created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Builder
    public User(int id, String name, String password, String email, String profileImageUrl, String field, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.field = field;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
