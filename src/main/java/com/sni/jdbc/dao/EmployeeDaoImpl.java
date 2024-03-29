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

    private static final String SQL_SAVE = "insert into employees (name, role) values (?, ?)";
    private static final String SQL_SAVE_ALL = "insert into employees (name, role) values (?, ?)";
    private static final String SQL_FIND_ONE =
            "select * from employees inner join buildings on employees.building_id = buildings.id where id=?";
    private static final String SQL_FIND_ALL = "select * from employees";
    private static final String SQL_DELETE = "delete from employees where id=?";
    private static final String SQL_DELETE_ALL = "delete from employees";

    @Override
    public <S extends Employee> S save(S entity) {

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE)) {
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

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ALL)) {
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

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ONE)) {
            preparedStatement.setInt(1, integer);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Employee employee = null;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String role = resultSet.getString("role");
                    employee = new Employee(id, name, role);
                }

                return Optional.ofNullable(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Employee> findAll() {

        Employee employee;
        List<Employee> employees;

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL); ResultSet resultSet = preparedStatement.executeQuery()) {
            employees = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                employee = new Employee(id, name, role);
                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Employee entity) {

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, entity.getId());
            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteAll() {

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ALL)) {
            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
