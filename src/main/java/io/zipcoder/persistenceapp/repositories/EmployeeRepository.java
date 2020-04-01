package io.zipcoder.persistenceapp.repositories;

import io.zipcoder.persistenceapp.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
    List<Employee> findAllByManagerId(Integer managerId);
    List<Employee> findAllByManagerNull();
    Employee findEmployeeById(Integer id);
    void deleteEmployeeById(Integer id);
}
