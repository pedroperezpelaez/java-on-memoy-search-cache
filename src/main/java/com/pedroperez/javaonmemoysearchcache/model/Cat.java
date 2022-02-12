package com.pedroperez.javaonmemoysearchcache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cat {
    private long id;
    private String name;
    private CatType type;
    private double weight;
    private double height;
    private double length;
    private int age;
    private String favouriteFood;
}
