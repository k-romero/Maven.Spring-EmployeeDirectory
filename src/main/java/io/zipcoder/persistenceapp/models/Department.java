package io.zipcoder.persistenceapp.models;

import io.zipcoder.persistenceapp.services.EmployeeService;

import javax.persistence.*;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer deptNumber;

    private String deptName;

    @OneToOne
    private Employee manager;

    public Department() {
    }

    public Department(String deptName, Integer managerId) {
        manager = new EmployeeService().show(managerId);
        this.deptName = deptName;
    }

    public Integer getDeptNumber() {
        return deptNumber;
    }

    public void setDeptNumber(Integer deptNumber) {
        this.deptNumber = deptNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Integer managerId) {
        this.manager = new EmployeeService().show(managerId);
    }
}
