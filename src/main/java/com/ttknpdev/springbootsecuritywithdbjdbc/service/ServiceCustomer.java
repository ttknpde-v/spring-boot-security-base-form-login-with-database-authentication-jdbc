package com.ttknpdev.springbootsecuritywithdbjdbc.service;

import com.ttknpdev.springbootsecuritywithdbjdbc.entities.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ServiceCustomer {
    private List<Customer> customers = new ArrayList<Customer>(List.of(
            new Customer("mark", (short) 23,"male",65000F),
            new Customer("alex", (short) 22,"male",35000F)
    ));
    public List<Customer> reads() {
        return customers;
    }

    public Customer read(String nickname , Short age , String gender , Float salary) {
        /*Customer customer = new Customer("mark", (short) 23,"male",65000F);
        System.out.println(customers.contains(customer));*/
        Customer customer = new Customer(nickname, age,gender,salary);
        if (customers.contains(customer)) {
            return customer;
        }
        else {
            return null;
        }
    }
    public void update(Customer customer , String nickname , Short age , String gender , Float salary) {
        //                      list , old data , new data
        Customer customerOld = new Customer(nickname,age,gender,salary);
        boolean check = Collections.replaceAll(customers,customerOld,customer);
        // System.out.println(check);
    }
    public void deleteByNickname(String nickname) {
        customers.removeIf(customer -> customer.getNickname().equals(nickname));
    }
    public void create(Customer customer) {
        customers.add(customer);
    }
}
