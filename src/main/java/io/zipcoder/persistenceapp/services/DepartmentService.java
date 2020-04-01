package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    public Department show(Integer id){
        return repository.findOne(id);
    }

    public Department create(Department dept){
        return repository.save(dept);
    }

    public Department update(Integer id, Department newDept){
        Department originalDepartment = repository.findOne(id);
        originalDepartment.setDeptName(newDept.getDeptName());
        originalDepartment.setManager(newDept.getManager());
        return repository.save(originalDepartment);
    }

    public Boolean delete(Integer id){
        repository.delete(id);
        return true;
    }

    public Department updateName(Integer id, String deptName){
        Department original = repository.findOne(id);
        original.setDeptName(deptName);
        return repository.save(original);
    }

    @Autowired
    private EmployeeService empService;

    public Department setManager(Integer deptId, Integer managerId){
        Department original = show(deptId);
        original.setManager(empService.show(managerId));
        return repository.save(original);
    }





}
