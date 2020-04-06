package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService service;



    // ------------------ BASIC CRUD



    @GetMapping(value = "/API/emp/show/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id){
        return this.service.show(id)
                .map(employee -> ResponseEntity
                                    .ok()
                                    .body(employee))
                .orElse(ResponseEntity
                            .notFound()
                            .build());
    }

    @RequestMapping("/API/emp/showAll")
    public ResponseEntity<Iterable<Employee>> index(){
        return new ResponseEntity<>(service.index(), HttpStatus.OK);
    }

    @PostMapping("/API/emp/create")
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        Employee newEmp = this.service.create(employee);

        try {
            return ResponseEntity
                    .created(new URI("/API/emp/create/" + newEmp.getId()))
                    .body(newEmp);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // ------------------ ALL UPDATES



    @PutMapping("/API/emp/updateByID{id}")
    public ResponseEntity<Employee> updateById(@RequestBody Employee employee,@PathVariable Integer id){
        return new ResponseEntity<>(service.update(id,employee), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updateFirstName/{id}")
    public ResponseEntity<?> updateFirstName(@RequestParam String firstName,@PathVariable Integer id){
        Optional<Employee> exsistingEmployee = service.show(id);
        return exsistingEmployee.map(e -> {
            e.setFirstName(firstName);
            try {
                return ResponseEntity
                        .ok()
                        .location(new URI("/API/emp/updateFirstName/" + e.getId()))
                        .body(e);
            }catch(URISyntaxException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/API/emp/updateLastName/{id}")
    public ResponseEntity<?> updateLastName(@RequestParam String lastName,@PathVariable Integer id){
        Optional<Employee> existingEmployee = service.show(id);
        return existingEmployee.map(e -> {
            e.setLastName(lastName);
            try {
                return ResponseEntity
                        .ok()
                        .location(new URI("/API/emp/updateLastName/" + e.getId()))
                        .body(e);
            }catch(URISyntaxException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/API/emp/updateManager/{id}")
    public ResponseEntity<Employee> updateManager(@RequestParam Integer managerId,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateEmpManager(id,managerId), HttpStatus.OK);
    }

    @PutMapping("/API/emp/updateDept/{id}")
    public ResponseEntity<Employee> updateDept(@RequestParam Integer deptNum,@PathVariable Integer id){
        return new ResponseEntity<>(service.updateDepartment(id,deptNum), HttpStatus.OK);
    }

    @PutMapping(value = "/API/emp/updatePhone/{id}", produces = (MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<?> updatePhone(@RequestParam String phone,@PathVariable Integer id){
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

    @PutMapping("/API/emp/setManager/{empId}")
    public ResponseEntity<Employee> setManager(@PathVariable Integer empId, @RequestParam Integer managerId){
        return new ResponseEntity<>(service.setManager(empId,managerId),HttpStatus.OK);
    }

    @RequestMapping("/API/emp/getAllManagers/{empId}")
    public ResponseEntity<ArrayList<Employee>> getReportingHierarchy(@PathVariable Integer empId){
        return new ResponseEntity<>(service.reportingHierarchy(empId),HttpStatus.OK);
    }

    @PutMapping("/API/emp/changeManager/{oldManagerId}")
    public ResponseEntity<List<Employee>> changeManager(@PathVariable Integer oldManagerId, @RequestParam Integer newManagerId){
        return new ResponseEntity<>(service.changeManager(oldManagerId,newManagerId),HttpStatus.OK);
    }

    @PutMapping("/API/manager/removeAndSwap/{managerToRemoveId}")
    public ResponseEntity<List<Employee>> removeManagerAndSwap(@PathVariable Integer managerToRemoveId){
        return new ResponseEntity<>(service.removeManagerAndSwapWithNextInLine(managerToRemoveId), HttpStatus.OK);
    }



    // ------------------ EMPLOYEE LIST



    @RequestMapping("API/emp/getByDept/{deptNum}")
    public ResponseEntity<ArrayList<Employee>> getEmpsByDept(@PathVariable Integer deptNum){
        return new ResponseEntity<>(service.getAllEmpsByDept(deptNum),HttpStatus.OK);
    }

    @RequestMapping("API/emp/getByManager/{managerNum}")
    public ResponseEntity<ArrayList<Employee>> getEmpsByManager(@PathVariable Integer managerNum){
        return new ResponseEntity<>(service.getAllEmpsByManagerId(managerNum),HttpStatus.OK);
    }

    @RequestMapping("/API/manager/getDirectReports/{managerId}")
    public ResponseEntity<ArrayList<Employee>> getDirectReports(@PathVariable Integer managerId){
        return new ResponseEntity<>(service.getAllDirectReports(managerId),HttpStatus.OK);
    }

    @RequestMapping("/API/manager/findEmployeesNoManager")
    public ResponseEntity<List<Employee>> findEmployeesNoManager(){
        return new ResponseEntity<>(service.findEmpsWithNoManager(),HttpStatus.OK);
    }

    @RequestMapping("/API/manager/getDirectAndIndirect/{managerId}")
    public ResponseEntity<HashSet<Employee>> findDirectAndInDirectReports(@PathVariable Integer managerId){
        return new ResponseEntity<>(service.findAllDirectAndIndirectReports(managerId), HttpStatus.OK);
    }

    @PutMapping("/API/emp/mergeDept")
    public ResponseEntity<Boolean> mergeDepartments(@RequestParam Integer deptNumToRemove,@RequestParam Integer deptNumToMergeInto){
        return new ResponseEntity<>(service.mergeDepartments(deptNumToRemove,deptNumToMergeInto),HttpStatus.OK);
    }



    // ------------------ GET EMPLOYEE ATTRIBUTES



    @RequestMapping("/API/emp/getDept/{id}")
    public ResponseEntity<Integer> getEmpDept(@PathVariable Integer id){
        return new ResponseEntity<>(service.getEmpDept(id),HttpStatus.OK);
    }

    @RequestMapping("/API/emp/getTitle/{id}")
    public ResponseEntity<String> getEmpTitle(@PathVariable Integer id){
        return new ResponseEntity<>(service.getEmpTitle(id),HttpStatus.OK);
    }

    @RequestMapping("/API/emp/getEmail/{id}")
    public ResponseEntity<String> getEmpEmail(@PathVariable Integer id){
        return new ResponseEntity<>(service.getEmpEmail(id),HttpStatus.OK);
    }



    // ------------------ REMOVE



    @PutMapping("API/emp/removeByDept/{deptNum}")
    public ResponseEntity<Boolean> removeDeptFromEmps(@PathVariable Integer deptNum, @RequestParam Integer newDept){
        return new ResponseEntity<>(service.removeEmpsFromDept(deptNum, newDept),HttpStatus.OK);
    }

    @DeleteMapping("/API/emp/removeEmp/{id}")
    public ResponseEntity<?> removeEmpById(@PathVariable Integer id){
        Optional<Employee> exsistingEmp = service.show(id);

        return exsistingEmp
                .map(e -> {
                    service.delete(e.getId());
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }


}
