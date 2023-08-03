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

@Data
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name = "FriendList")
public class FriendList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;
    @Column(name = "friend_id")
    private int friendId;

    @Column(name = "date")
    @CreationTimestamp
    private Timestamp date;

    @Builder
    public FriendList(int id, int userId, int friendId, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.date = date;
    }
}
