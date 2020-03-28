package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService service;

    @GetMapping("/API/dept/show/{deptNumber}")
    public ResponseEntity<Department> show(@PathVariable Integer deptNumber){
        return new ResponseEntity<>(service.show(deptNumber), HttpStatus.OK);
    }

    @PostMapping("/API/dept/create")
    public ResponseEntity<Department> create(@RequestBody Department dept){
        return new ResponseEntity<>(service.create(dept),HttpStatus.CREATED);
    }

    @PutMapping("/API/dept/updateName/{deptNumber}")
    public ResponseEntity<Department> update(@RequestParam String deptName, @PathVariable Integer deptNumber){
        return new ResponseEntity<>(service.updateName(deptNumber,deptName),HttpStatus.OK);
    }

    @PutMapping("/API/dept/updateManager/{deptId}")
    public ResponseEntity<Department> updateManager(@RequestParam Integer managerId, @PathVariable Integer deptId){
        return new ResponseEntity<>(service.updateManager(deptId,managerId), HttpStatus.OK);
    }

}
