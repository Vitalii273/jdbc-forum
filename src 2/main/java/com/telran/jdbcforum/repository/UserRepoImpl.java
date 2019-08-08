package com.telran.jdbcforum.repository;

import com.telran.jdbcforum.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepository {

    @Autowired
    DataSource source;


    private PasswordEncoder encoder;

    public UserRepoImpl(PasswordEncoder encoder) {
        this.encoder = encoder;

    }

    @Override
    public void addUser(UserEntity userEntity) {
        String sql = "INSERT INTO users (username,password) VALUES(?,?)";
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userEntity.getUsername());
            ps.setString(2, userEntity.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User " + userEntity.getUsername() + " already exists");
        }
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username=?");
            ps.setString(1, String.valueOf(username));
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result.next()) {
                UserEntity entity = getUserEntity(result);
                return entity;
            }
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + username + " is not found");
    }

    @NotNull
    private UserEntity getUserEntity(ResultSet result) throws SQLException {
        UserEntity entity = new UserEntity();
        entity.setUsername(result.getString("username"));
        entity.setPassword(result.getString("password"));
        return entity;
    }

    @Override
    public void removeUser(String username) {
        if (getUserByUsername(username) != null) {
            String sql = "DELETE FROM users WHERE username = ?";
            try (Connection conn = source.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> res = new ArrayList<>();
        try (Connection connection = source.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM users");
            while (set.next()) {
                UserEntity entity = getUserEntity(set);
                res.add(entity);
            }
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return res;
    }
}
