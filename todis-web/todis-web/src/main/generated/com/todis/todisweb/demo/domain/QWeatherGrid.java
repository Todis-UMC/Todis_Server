package com.todis.todisweb.demo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWeatherGrid is a Querydsl query type for WeatherGrid
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeatherGrid extends EntityPathBase<WeatherGrid> {

    private static final long serialVersionUID = 1451544502L;

    public static final QWeatherGrid weatherGrid = new QWeatherGrid("weatherGrid");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> nx = createNumber("nx", Integer.class);

    public final NumberPath<Integer> ny = createNumber("ny", Integer.class);

    public final StringPath regionBig = createString("regionBig");

    public final StringPath regionMiddle = createString("regionMiddle");

    public final StringPath regionSmall = createString("regionSmall");

    public QWeatherGrid(String variable) {
        super(WeatherGrid.class, forVariable(variable));
    }

    public QWeatherGrid(Path<? extends WeatherGrid> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeatherGrid(PathMetadata metadata) {
        super(WeatherGrid.class, metadata);
    }

}

