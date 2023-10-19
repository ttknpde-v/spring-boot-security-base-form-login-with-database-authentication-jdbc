package com.ttknpdev.springbootsecuritywithdbjdbc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String brand;
    private String model;
    private Double price;
}
