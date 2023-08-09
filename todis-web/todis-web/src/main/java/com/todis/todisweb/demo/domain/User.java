package com.todis.todisweb.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;


@Data
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name="Member")
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
    @Column(name="gender")
    private String gender;
    @Column(name="profile_image_url")
    private String profileImageUrl;
    @Column(name="cody_image")
    private String codyImage;
    @Column(name="provider")
    private String provider;
    @Column(name="created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Builder
    public User(int id, String name, String password, String email, String gender, String profileImageUrl, String provider, Timestamp createdAt, Timestamp updatedAt) {
  
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.provider = provider;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
