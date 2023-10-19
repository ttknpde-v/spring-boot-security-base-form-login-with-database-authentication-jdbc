package com.ttknpdev.springbootsecuritywithdbjdbc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String nickname;
    private Short age;
    private String gender;
}
