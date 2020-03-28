package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping(value = "/API/getAll")
    public ResponseEntity<Employee> show(@PathVariable Integer id){
        return new ResponseEntity<>(service.show(id),HttpStatus.OK);
    }

//    @RequestMapping
//    public ResponseEntity<Iterable<Employee>> index(){
//        return new ResponseEntity<>(service.index(), HttpStatus.OK);
//    }

    @PostMapping("/API")
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        return new ResponseEntity<>(service.create(employee), HttpStatus.CREATED);
    }

    @PutMapping("/API/{id}")
    public ResponseEntity<Employee> updateById(@RequestBody Employee employee,@PathVariable Integer id){
        return new ResponseEntity<>(service.update(id,employee), HttpStatus.OK);
    }

}
