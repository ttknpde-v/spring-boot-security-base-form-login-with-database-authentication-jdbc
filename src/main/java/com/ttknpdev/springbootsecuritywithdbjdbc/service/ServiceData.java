package com.ttknpdev.springbootsecuritywithdbjdbc.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ServiceData extends ServiceMethod {

    @Data
    @AllArgsConstructor
    static
    class Student { // inner class
        public String firstname;
        public String lastname;
        public Short age;
    } // ended inner class

    @Getter
    private final List<Student> data;
    public ServiceData() {
        data = new ArrayList<>();
        data.add(new Student("peter", "parker", (short) 21));
        data.add(new Student("mark", "ryder", (short) 21));
        data.add(new Student("adam", "sandler", (short) 21));
    }

    public Student getData1 () {
        return data.get(0);
    }
    public Student getData2 () {
        return data.get(1);
    }
    public Student getDataOnlySpecify(int element) {
        if (element >= 3) {
            return new Student("Ajax", "ryder", (short) 22);
        }
        else {
            return null;
        }
    }
}
