package com.ttknpdev.springbootsecuritywithdbjdbc.service;

import com.ttknpdev.springbootsecuritywithdbjdbc.entities.Student;

import java.util.ArrayList;
import java.util.List;

public class ServiceDataSecond {
    private List<Student> studentList;

    public ServiceDataSecond() {
        this.studentList = new ArrayList<>();
        this.studentList.add(new Student("Peter",(short)21,"Female"));
        this.studentList.add(new Student("Alex",(short)22,"Female"));
        this.studentList.add(new Student("Smite",(short)20,"Female"));
    }

    public List<Student> getStudentList() {
        return studentList;
    }
}
