package com.ttknpdev.springbootsecuritywithdbjdbc.controller;

import com.ttknpdev.springbootsecuritywithdbjdbc.entities.Car;
import com.ttknpdev.springbootsecuritywithdbjdbc.entities.Customer;
import com.ttknpdev.springbootsecuritywithdbjdbc.entities.Student;
import com.ttknpdev.springbootsecuritywithdbjdbc.service.ServiceCustomer;
import com.ttknpdev.springbootsecuritywithdbjdbc.service.ServiceData;
import com.ttknpdev.springbootsecuritywithdbjdbc.service.ServiceDataSecond;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControlPath {
    private ServiceData serviceData ;
    private  ServiceDataSecond serviceDataSecond;
    private  ServiceCustomer serviceCustomer ;

    public ControlPath() {
        this.serviceData = new ServiceData();
        this.serviceDataSecond  = new ServiceDataSecond();
        this.serviceCustomer = new ServiceCustomer();
    }

    @GetMapping
    private ResponseEntity<?> test() {
        /*
            Anyone can access default path /
            No path authentication
        */
        return ResponseEntity.ok(serviceData.getData());
    }


    // ####### for the first html page
    @GetMapping(value = "/one/{element}")
    private ModelAndView home (ModelAndView modelAndView , @PathVariable Integer element){
        modelAndView.setViewName("home");
        if (element == 1) {
            modelAndView.addObject("data1",serviceData.getData1());
        }
        else if (element == 2) {
            modelAndView.addObject("data2",serviceData.getData2());
        }
        else if (element == 3){
            modelAndView.addObject("dataHasArgument", serviceData.getDataOnlySpecify(element));
        }
        else if (element >= 4) {
            modelAndView.addObject("data1",serviceData.getData1());
            modelAndView.addObject("data2",serviceData.getData2());
            modelAndView.addObject("dataHasArgument", serviceData.getDataOnlySpecify(element));
        }
        return modelAndView;
    }


    // ####### for the second html page
    @GetMapping(value = "/two")
    private ModelAndView homeTwo (ModelAndView modelAndView ){
        modelAndView.addObject("title","Welcome to Second Page (Working with Method)");
        /* (For second way) have to create */
        // Empty Student model object to store form data
        // Must set default empty
        Student forStudentForm = new Student();
        modelAndView.addObject("student",forStudentForm);
        modelAndView.setViewName("home-two");
        return modelAndView;
    }


    /*
        (First)
        *****
        these are way how to map tag html together attribute Java
        *****
    */
    @PostMapping(value = "/get-factorial")
    private ModelAndView getFactorial (ModelAndView modelAndView , Integer n) { /* it maps a tag input auto */
        if (n == null) {
            // modelAndView.addObject("resultFactorial",serviceData.factorial(1));
            modelAndView.setViewName("redirect:/two");
        }else {
            modelAndView.addObject("title","Your input was "+n);
            modelAndView.addObject("resultFactorial",serviceData.factorial(n));
            modelAndView.setViewName("home-two");
        }
        return modelAndView;
    }
    /*
        (Second way)
        *****
        Map by th:object=""
        *****
    */
    @PostMapping(value = "/map-student")
    private ModelAndView getStudent(ModelAndView modelAndView , @ModelAttribute("student") Student student) {
        /*
            map by th:object it'll map Java pojo auto
        */
        if (student.getNickname().trim().isEmpty() || student.getAge() == null) {
            modelAndView.setViewName("redirect:/two");
        } else {
            modelAndView.addObject("title","Your input were "+student);
            modelAndView.addObject("student",student);
            modelAndView.setViewName("home-two");

        }
        return modelAndView;
    }
    /*
        it maps a tag input auto
        (name input tage it should be same name argument)
    */
    @PostMapping(value = "/map-car")
    private ModelAndView getCar(ModelAndView modelAndView,String brand,String model,Double price) {
        Car car = new Car(brand,model,price);
        if (car.getBrand().trim().isEmpty() || car.getModel().trim().isEmpty() || car.getPrice() == null) {
            /*car.setBrand("Ford");
            car.setModel("Ranger XLS");
            car.setPrice(790000D);
            modelAndView.addObject("car",car);*/
            modelAndView.setViewName("redirect:/two");
        }
        else {
            modelAndView.addObject("title","Your input were "+brand+","+model+","+price);
            modelAndView.addObject("car",car);
            modelAndView.setViewName("home-two");
        }
        return modelAndView;
    }



    // ####### for the third html page
    @GetMapping(value = "/three")
    private ModelAndView homeThird (ModelAndView modelAndView ){
        modelAndView.addObject("title","FORM -- CREATE --");
        Customer forCustomerForm = new Customer();
        modelAndView.addObject("customer",forCustomerForm);
        modelAndView.setViewName("home-three");
        return modelAndView;
    }
    /*
    (third way)
    *****
    Map by th:object="" & th:field=""
    @ModelAttribute("customer") map to th:object=""
    *****
*/
    @PostMapping(value = "/map-customer")
    private ModelAndView getCustomer(ModelAndView modelAndView , @ModelAttribute("customer") Customer customer) {
        // modelAndView.addObject("title","Your input were "+customer);
        if (customer.getAge() != null && !customer.getNickname().trim().isEmpty() && !customer.getGender().trim().isEmpty()) {
            serviceCustomer.create(customer);
            modelAndView.setViewName("redirect:/three/reads");
        }
        else {
            modelAndView.setViewName("redirect:/three");
        }
        return modelAndView;
    }
    @GetMapping(value = "/three/reads")
    private ModelAndView homeThirdReads (ModelAndView modelAndView ){
        modelAndView.addObject("title","FORM -- READS --");
        modelAndView.addObject("listCustomers",serviceCustomer.reads());
        modelAndView.setViewName("home-three-second");
        return modelAndView;
    }

    @GetMapping(value = "/three/delete/{nickname}")
    private ModelAndView deleteByNickname( ModelAndView modelAndView, @PathVariable String nickname) {
        serviceCustomer.deleteByNickname(nickname); // delete by nickname
        modelAndView.setViewName("redirect:/three/reads");
        return modelAndView;
    }
    @GetMapping(value = "/three/update/{nickname}/{age}/{gender}/{salary}")
    private ModelAndView updateForm(@PathVariable String nickname,
                                    @PathVariable Short age,
                                    @PathVariable String gender,
                                    @PathVariable Float salary, ModelAndView modelAndView) {
        modelAndView.addObject("title","FORM -- UPDATE --");
        Customer customer = serviceCustomer.read(nickname,age,gender,salary);
        modelAndView.addObject("customer",customer);
        modelAndView.setViewName("home-three-third");
        return modelAndView;
    }

    @PostMapping(value = "/update")
    private ModelAndView update(ModelAndView modelAndView ,@ModelAttribute Customer customer ,
                                String nicknameOld ,
                                Short ageOld ,
                                String gender,
                                Float salaryOld) {
        serviceCustomer.update(customer,nicknameOld,ageOld,gender,salaryOld);
        modelAndView.setViewName("redirect:/three/reads");
        return modelAndView;
    }

    // ####### for the third html page
    @GetMapping(value = "/four/{c}")
    private ModelAndView homeCondition (ModelAndView modelAndView , @PathVariable String c){
        if (c.equals("1")) {
            modelAndView.setViewName("home-two");
        }
        else if(c.equals("2")) {
            Customer customer = new Customer();
            modelAndView.setViewName("home-three");
            modelAndView.addObject("customer",customer);
        }
        else if (c.equals("3")) {
            modelAndView.addObject("title","This way I show you How to loop list object");
            modelAndView.addObject("list",serviceDataSecond.getStudentList());
            modelAndView.setViewName("home-fourth");
        }
        return modelAndView;
    }







}
