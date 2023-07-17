package com.todis.todisweb.demo.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name="LikeCody")
public class LikeCody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int keyId;

    @Column(name="user_id")
    private int userId;
    @Column(name="coordination_id")
    private int coordinationId;

    @Builder
    public LikeCody(int keyID, int userId, int coordinationId) {
        this.keyId = keyId;
        this.userId = userId;
        this.coordinationId = coordinationId;
    }
}
