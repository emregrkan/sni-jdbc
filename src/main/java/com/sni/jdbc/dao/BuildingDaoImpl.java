package com.sni.jdbc.dao;

import com.sni.jdbc.data.DataSource;
import com.sni.jdbc.entity.Building;
import com.sni.jdbc.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuildingDaoImpl implements BuildingDao {

    private static final String SQL_SAVE = "insert into buildings (address, working_employees) values (?, ?)";
    private static final String SQL_SAVE_ALL = "insert into buildings (address, working_employees) values (?, ?)";
    private static final String SQL_FIND_ONE = "select * from buildings where id=?";
    private static final String SQL_FIND_ALL = "select * from buildings";
    private static final String SQL_DELETE = "delete from buildings where id=?";
    private static final String SQL_DELETE_ALL = "delete from buildings";

    @Override
    public <S extends Building> S save(S entity) {

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, entity.getAddress());
            preparedStatement.setInt(2, entity.getWorkingEmployees());
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0 ? entity : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <S extends Building> Iterable<S> saveAll(Iterable<S> entities) {

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ALL)) {
            int i = 0;

            for (S entity : entities) {
                preparedStatement.setString(1, entity.getAddress());
                preparedStatement.setInt(2, entity.getWorkingEmployees());
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
    public Optional<Building> findOne(Integer integer) {

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ONE)) {
            preparedStatement.setInt(1, integer);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Building building = null;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String address = resultSet.getString("address");
                    int workingEmployees = resultSet.getInt("working_employees");
                    building = new Building(id, address, workingEmployees);
                }

                return Optional.ofNullable(building);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Building> findAll() {

        Building building;
        List<Building> buildings;

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL); ResultSet resultSet = preparedStatement.executeQuery()) {
            buildings = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String address = resultSet.getString("address");
                int workingEmployees = resultSet.getInt("working_employees");
                building = new Building(id, address, workingEmployees);
                buildings.add(building);
            }

            return buildings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Building entity) {

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
