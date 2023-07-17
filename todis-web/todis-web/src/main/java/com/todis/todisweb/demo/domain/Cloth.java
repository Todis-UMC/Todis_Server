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
@Table(name="Cloth")
public class Cloth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="size")
    private String size;
    @Column(name="material")
    private String material;
    @Column(name="season")
    private String season;
    @Column(name="type")
    private String type;
    @Column(name="color")
    private String color;

    @Builder
    public Cloth(int id, String name, String size, String material, String season, String type, String color) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.material = material;
        this.season = season;
        this.type = type;
        this.color = color;
    }
}
