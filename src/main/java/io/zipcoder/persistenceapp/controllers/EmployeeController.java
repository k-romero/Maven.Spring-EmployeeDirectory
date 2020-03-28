package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService service;

    // ------------------ BASIC CRUD

    @GetMapping(value = "/API/emp/show/{id}")
    public ResponseEntity<Employee> show(@PathVariable Integer id){
        return new ResponseEntity<>(service.show(id),HttpStatus.OK);
    }

    @RequestMapping("/API/emp/showAll")
    public ResponseEntity<Iterable<Employee>> index(){
        return new ResponseEntity<>(service.index(), HttpStatus.OK);
    }

    @PostMapping("/API/emp/create")
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        return new ResponseEntity<>(service.create(employee), HttpStatus.CREATED);
    }


    // ------------------ ALL UPDATES


    @PutMapping("/API/emp/updateByID{id}")
    public ResponseEntity<Employee> updateById(@RequestBody Employee employee,@PathVariable Integer id){
        return new ResponseEntity<>(service.update(id,employee), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updateFirstName/{id}")
    public ResponseEntity<Employee> updateFirstName(@RequestParam String firstName,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateFirstName(id,firstName), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updateLastName/{id}")
    public ResponseEntity<Employee> updateLastName(@RequestParam String lastName,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateLastName(id,lastName), HttpStatus.OK);
    }


    @PutMapping("/API/emp/updateManager/{id}")
    public ResponseEntity<Employee> updateManager(@RequestParam Integer managerId,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateEmpManager(id,managerId), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updateDept/{id}")
    public ResponseEntity<Employee> updateDept(@RequestParam Integer deptNum,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateDepartment(id,deptNum), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updatePhone/{id}")
    public ResponseEntity<Employee> updatePhone(@RequestParam String phone,@PathVariable Integer id){
        return new ResponseEntity<>(service.updatePhone(id,phone), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updateEmail/{id}")
    public ResponseEntity<Employee> updateEmail(@RequestParam String email,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateEmail(id,email), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updateTitle/{id}")
    public ResponseEntity<Employee> updateTitle(@RequestParam String title,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateTitle(id,title), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updateHireDate/{id}")
    public ResponseEntity<Employee> updateHireDate(@RequestParam String hireDate,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateHireDate(id,hireDate), HttpStatus.OK);
    }


    // ------------------ MANAGER GET/SET


    @RequestMapping("/API/emp/getManager/{id}")
    public ResponseEntity<Employee> getEmpManager(@PathVariable Integer id){
        return new ResponseEntity<>(service.getManager(id),HttpStatus.OK);
    }

    //TODO change method name to update once resolved

    @PutMapping("/API/emp/updateManager/{empId}")
    public ResponseEntity<Employee> setManager(@PathVariable Integer empId, @RequestParam Integer managerId){
        return new ResponseEntity<>(service.setManager(empId,managerId),HttpStatus.OK);
    }


    // ------------------ EMPLOYEE LIST

    @RequestMapping("API/emp/getByDept/{deptNum}")
    public ResponseEntity<ArrayList<Employee>> getEmpsByDept(@PathVariable Integer deptNum){
        return new ResponseEntity<>(service.getAllEmpsByDept(deptNum),HttpStatus.OK);
    }

    @PutMapping("API/emp/removeByDept{deptNum}")
    public ResponseEntity<Boolean> removeDeptFromEmps(@PathVariable Integer deptNum, @RequestParam Integer newDept){
       return new ResponseEntity<>(service.removeEmpsFromDept(deptNum, newDept),HttpStatus.OK);
    }


}
