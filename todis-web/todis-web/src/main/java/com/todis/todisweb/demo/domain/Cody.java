package com.todis.todisweb.demo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Time;
import java.sql.Timestamp;


@Data
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name="Cody")
public class Cody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private int userId;
    @Column(name="cloth_id")
    private int clothId;
    @Column(name="comment")
    private String comment;
    @Column(name="likes")
    private int likes;
    @Column(name="image")
    private String image;

    @Column(name="date")
    @CreationTimestamp
    private Timestamp date;

    @Builder
    public Cody(int id, int userId, int clothId, String comment, int likes, String image, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.clothId = clothId;
        this.comment = comment;
        this.likes = likes;
        this.image = image;
        this.date = date;
    }
}
