package com.todis.todisweb.demo.domain;

import jakarta.persistence.*;
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
@Table(name="Friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private int userId;
    @Column(name="friend_id")
    private int friendId;
    @Column(name="is_allowed")
    private Boolean isAllowed;

    @Column(name="date")
    @CreationTimestamp
    private Timestamp date;

}
