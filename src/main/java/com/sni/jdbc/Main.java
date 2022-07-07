package com.sni.jdbc;

import com.sni.jdbc.dao.EmployeeDao;
import com.sni.jdbc.entity.Employee;

public class Main {
    public static void main(String[] args) {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.findOne(1000).orElse(null);
        boolean isDeleted = employeeDao.delete(employee);
        System.out.println(isDeleted);
        employeeDao.findAll().forEach(System.out::println);
    }
}