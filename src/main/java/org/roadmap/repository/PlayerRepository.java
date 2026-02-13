package org.roadmap.repository;

import org.roadmap.model.entity.PlayerEntity;
import org.roadmap.util.ConnectionManagerUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerRepository {
    private final static String SAVE = "INSERT INTO players (name) values (?)";

    public PlayerEntity save(PlayerEntity playerEntity) {
        try (Connection connection = ConnectionManagerUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, playerEntity.getName());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Integer id = generatedKeys.getInt(1);
                    return new PlayerEntity(id, playerEntity.getName());
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return null;
    }
}
