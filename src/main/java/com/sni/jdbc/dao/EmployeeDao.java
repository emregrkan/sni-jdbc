package com.sni.jdbc.dao;

import com.sni.jdbc.entity.Employee;
import com.sni.jdbc.repository.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDao implements Dao<Employee, Integer> {

    @Override
    public <S extends Employee> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Employee> Iterable<S> saveAll(Iterable<S> entity) {
        return null;
    }

    @Override
    public Optional<Employee> findOne(Integer integer) {
        String SQL = "select * from employees where id=?";

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            preparedStatement.setInt(1, integer);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Employee employee = null;

                while (resultSet.next()) {
                    employee = new Employee();
                    employee.setId(resultSet.getInt("id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setRole(resultSet.getString("role"));
                }

                return Optional.ofNullable(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Employee> findAll() {
        String SQL = "select * from employees";
        Employee employee;
        List<Employee> employees;

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL); ResultSet resultSet = preparedStatement.executeQuery()) {
            employees = new ArrayList<>();

            while (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setRole(resultSet.getString("role"));
                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Employee entity) {
        String SQL = "delete from employees where id=?";

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            preparedStatement.setInt(1, entity.getId());
            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
