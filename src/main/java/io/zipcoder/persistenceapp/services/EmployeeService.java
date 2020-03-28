package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    public Employee show(Integer id){
        return repository.findOne(id);
    }

    public Iterable<Employee> index(){
        return repository.findAll();
    }

    public Employee create(Employee employee){
        return repository.save(employee);
    }

    public Employee update(Integer id, Employee newEmp){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setFirstName(newEmp.getFirstName());
        originalEmployee.setLastName(newEmp.getLastName());
        originalEmployee.setDeptNumber(newEmp.getDeptNumber());
        originalEmployee.setEmail(newEmp.getEmail());
        originalEmployee.setManager(newEmp.getManagerId());
        originalEmployee.setPhoneNumber(newEmp.getPhoneNumber());
        originalEmployee.setTitle(newEmp.getPhoneNumber());
        originalEmployee.setHireDate(newEmp.getHireDate());
        return repository.save(originalEmployee);
    }

    public Employee updateFirstName(Integer id, String firstN){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setFirstName(firstN);
        return repository.save(originalEmployee);
    }

    public Employee updateLastName(Integer id, String lastN){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setLastName(lastN);
        return repository.save(originalEmployee);
    }

    public Employee updateEmpManager(Integer id, Integer managerId){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setManager(managerId);
        return repository.save(originalEmployee);
    }

    public Employee updateDepartment(Integer id, Integer deptNumber){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setDeptNumber(deptNumber);
        return repository.save(originalEmployee);
    }

    public Employee updateEmail(Integer id, String email){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setEmail(email);
        return repository.save(originalEmployee);
    }

    public Employee updateTitle(Integer id, String title){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setTitle(title);
        return repository.save(originalEmployee);
    }

    public Employee updateHireDate(Integer id, String hireDate){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setHireDate(hireDate);
        return repository.save(originalEmployee);
    }

    public Employee updatePhone(Integer id, String phone){
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setPhoneNumber(phone);
        return repository.save(originalEmployee);
    }



    public Boolean delete(Integer id){
        repository.delete(id);
        return true;
    }
}
