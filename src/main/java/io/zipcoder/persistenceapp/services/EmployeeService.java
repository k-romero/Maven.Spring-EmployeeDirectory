package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Optional<Employee> show(Integer id){
        return Optional.ofNullable(repository.findEmployeeById(id));
    }

    public Employee getEmployee(Integer id){
        return repository.findEmployeeById(id);
    }

    public Iterable<Employee> index(){
        return repository.findAll();
    }

    public Employee create(Employee employee){
        return repository.save(employee);
    }

    public Employee update(Integer id, Employee newEmp){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setFirstName(newEmp.getFirstName());
        originalEmployee.setLastName(newEmp.getLastName());
        originalEmployee.setDeptNumber(newEmp.getDeptNumber());
        originalEmployee.setEmail(newEmp.getEmail());
        originalEmployee.setManager(newEmp.getManager());
        originalEmployee.setPhoneNumber(newEmp.getPhoneNumber());
        originalEmployee.setTitle(newEmp.getPhoneNumber());
        originalEmployee.setHireDate(newEmp.getHireDate());
        return repository.save(originalEmployee);
    }

    public Employee updateFirstName(Integer id, String firstN){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setFirstName(firstN);
        return repository.save(originalEmployee);
    }

    public Employee updateLastName(Integer id, String lastN){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setLastName(lastN);
        return repository.save(originalEmployee);
    }

    public Employee updateEmpManager(Integer id, Integer managerId){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setManager(repository.findEmployeeById(managerId));
        return repository.save(originalEmployee);
    }

    public Employee updateDepartment(Integer id, Integer deptNumber){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setDeptNumber(deptNumber);
        return repository.save(originalEmployee);
    }

    public Employee updateEmail(Integer id, String email){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setEmail(email);
        return repository.save(originalEmployee);
    }

    public Employee updateTitle(Integer id, String title){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setTitle(title);
        return repository.save(originalEmployee);
    }

    public Employee updateHireDate(Integer id, String hireDate){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setHireDate(hireDate);
        return repository.save(originalEmployee);
    }

    public Employee updatePhone(Integer id, String phone){
        Employee originalEmployee = repository.findEmployeeById(id);
        originalEmployee.setPhoneNumber(phone);
        return repository.save(originalEmployee);
    }

    public Employee setManager(Integer employeeId, Integer managerId){
        Employee emp = repository.findEmployeeById(employeeId);
        emp.setManager(repository.findEmployeeById(managerId));
        return repository.save(emp);
    }

    public Employee getManager(Integer empId){
        return repository.findEmployeeById(empId).getManager();
    }

    public Boolean delete(Integer id){
        repository.deleteEmployeeById(id);
        return true;
    }

    public ArrayList<Employee> getAllEmpsByDept(Integer deptNumber){
        ArrayList<Employee> allEmps = (ArrayList<Employee>) index();
        return (ArrayList<Employee>) allEmps.stream()
                .filter(e -> e.getDeptNumber() == deptNumber)
                .collect(Collectors.toList());
    }

    public ArrayList<Employee> getAllEmpsByManagerId(Integer managerId){
        return (ArrayList<Employee>) repository.findAllByManagerId(managerId);
    }

    public ArrayList<Employee> getAllDirectReports(Integer managerId){
        ArrayList<Employee> allEmps = (ArrayList<Employee>) index();
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee e : allEmps) {
            if(e.getManager() != null){
                if(e.getManager().getId() == managerId){
                    result.add(e);
                }
            }
        }
        return result;
    }

    public boolean removeEmpsFromDept(Integer deptNumber, Integer newDeptNum){
        getAllEmpsByDept(deptNumber).forEach(employee -> {
            employee.setDeptNumber(newDeptNum);
            repository.save(employee);
        });
        return true;
    }

    public ArrayList<Employee> reportingHierarchy(Integer employeeId){
        ArrayList<Employee> result = new ArrayList<>();
        Employee emp = repository.findEmployeeById(employeeId);
        while(emp.getManager() != null){
            result.add(emp.getManager());
            emp = emp.getManager();
        }
        return result;
    }

    public List<Employee> changeManager(Integer oldManagerId, Integer newManagerId){
       List<Employee> employeeList = repository.findAllByManagerId(oldManagerId);
       Employee newManager = repository.findEmployeeById(newManagerId);
       employeeList.forEach(e -> e.setManager(newManager));
       repository.saveAll(employeeList);
       return employeeList;
    }

    public List<Employee> removeManagerAndSwapWithNextInLine(Integer managerToRemoveId){
        return changeManager(managerToRemoveId,getManager(managerToRemoveId).getId());
    }

    public List<Employee> findEmpsWithNoManager(){
        return repository.findAllByManagerNull();
    }

    public HashSet<Employee> findAllDirectAndIndirectReports(Integer managerId){
        ArrayList<Employee> resultList = new ArrayList<>(getAllEmpsByManagerId(managerId));
        for (int i = 0; i < resultList.size(); i++) {
            resultList.addAll(findAllDirectAndIndirectReports(resultList.get(i).getId()));
        }
        return new HashSet<>(resultList);
    }

    public Integer getEmpDept(Integer empId){
        return repository.findEmployeeById(empId).getDeptNumber();
    }

    public String getEmpTitle(Integer empId){
        return repository.findEmployeeById(empId).getTitle();
    }

    public String getEmpEmail(Integer empId){
        return repository.findEmployeeById(empId).getEmail();
    }

    @Autowired
    DepartmentService deptService;

    public boolean mergeDepartments(Integer deptNumToRemove, Integer deptNumToMergeInto){
        Employee oldDeptManager = deptService.show(deptNumToRemove).getManager();
        Employee newManager = deptService.show(deptNumToMergeInto).getManager();
        List<Employee> oldDeptEmployees = getAllEmpsByDept(deptNumToRemove);
        oldDeptEmployees.forEach(employee -> {
            employee.setDeptNumber(deptNumToMergeInto);
            repository.save(employee);
        });
        changeManager(oldDeptManager.getId(),newManager.getId());
        return true;
    }



}
