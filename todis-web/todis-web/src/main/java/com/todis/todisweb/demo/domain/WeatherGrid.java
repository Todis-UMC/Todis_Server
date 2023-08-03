package com.todis.todisweb.demo.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name="WeatherGrid")
public class WeatherGrid {

    @Id
    private int id;

    private String regionBig ;
    private String regionMiddle ;
    private String regionSmall ;
    private int nx;
    private int ny;
}

