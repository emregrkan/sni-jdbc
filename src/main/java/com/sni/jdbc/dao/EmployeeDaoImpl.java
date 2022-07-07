package com.sni.jdbc.dao;

import com.sni.jdbc.data.DataSource;
import com.sni.jdbc.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public <S extends Employee> S save(S entity) {
        String SQL = "insert into employees (name, role) values (?, ?)";

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getRole());
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0 ? entity : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <S extends Employee> Iterable<S> saveAll(Iterable<S> entities) {
        String SQL = "insert into employees (name, role) values (?, ?)";

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            int i = 0;

            for (S entity : entities) {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getRole());
                preparedStatement.addBatch();
                i++;
            }

            preparedStatement.executeBatch();
            System.out.println("Saved " + i + " entities.");
            return entities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Employee> findOne(Integer integer) {
        String SQL = "select * from employees where id=?";

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
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

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, entity.getId());
            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteAll() {
        String SQL = "delete from employees";

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
