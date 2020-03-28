package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository repository;

    public Department show(Integer id){
        return repository.findOne(id);
    }

    public Department create(Department dept){
        return repository.save(dept);
    }

    public Department update(Integer id, Department newDept){
        Department originalDepartment = repository.findOne(id);
        originalDepartment.setDeptName(newDept.getDeptName());
        originalDepartment.setManagerId(newDept.getManagerId());
        return repository.save(originalDepartment);
    }

    public Boolean delete(Integer id){
        repository.delete(id);
        return true;
    }


}
