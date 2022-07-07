package com.sni.jdbc;

import com.sni.jdbc.dao.BuildingDao;
import com.sni.jdbc.dao.BuildingDaoImpl;
import com.sni.jdbc.dao.EmployeeDao;
import com.sni.jdbc.dao.EmployeeDaoImpl;


public class Main {
    public static void main(String[] args) {
        BuildingDao buildingDao = new BuildingDaoImpl();
        buildingDao.findAll().forEach(System.out::println);
    }
}