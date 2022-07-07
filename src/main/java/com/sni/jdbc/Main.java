package com.sni.jdbc;

import com.sni.jdbc.dao.EmployeeDao;
import com.sni.jdbc.dao.EmployeeDaoImpl;


public class Main {
    public static void main(String[] args) {
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        employeeDao.findAll().forEach(System.out::println);
    }
}