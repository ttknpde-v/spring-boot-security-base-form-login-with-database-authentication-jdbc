package com.ttknpdev.springbootsecuritywithdbjdbc.service;

public class ServiceMethod {
    public Integer factorial(int n) {
        int factorial = 1;
        if (n <= 30) {
            for (int i = n; i >=  1; i--) {
                factorial *= i;
            }
        }
        else {
            return factorial;
        }
        return factorial;
    }
}
