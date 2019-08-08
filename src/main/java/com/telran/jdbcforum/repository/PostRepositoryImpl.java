package com.telran.jdbcforum.repository;

import com.telran.jdbcforum.entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {
    @Autowired
    DataSource source;

    @Override
    public List<PostEntity> getAllPosts() {
        List<PostEntity> res = new ArrayList<>();
        try (Connection connection = source.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM posts");
            while (set.next()) {
                PostEntity entity = new PostEntity();
                entity.setId(set.getInt("id"));
                entity.setContent(set.getString("content"));
                entity.setDateTime(set.getTimestamp("date_time"));
                entity.setUserName(set.getString("userName"));
                res.add(entity);
            }
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return res;
    }

    @Override
    public PostEntity getPostById(int id) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM posts WHERE id=?");
            ps.setString(1, String.valueOf(id));
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result.next()) {
                PostEntity entity = new PostEntity();
                entity.setId(result.getInt("id"));
                entity.setContent(result.getString("content"));
                entity.setDateTime(result.getTimestamp("date_time"));
                entity.setUserName(result.getString("userName"));
                return entity;
            }
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + id + " is not exist");
    }

    @Override
    public void addPost(PostEntity postEntity) {
        String sql = "INSERT INTO posts (content,date_time, username) VALUES(?,?,?)";
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, postEntity.getContent());
            ps.setTimestamp(2, postEntity.getDateTime());
            ps.setString(3, postEntity.getUserName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User " + postEntity.getUserName() + " does not exists");
        }
    }

    @Override
    public void removePost(int id, String username) {
        String sql = "DELETE FROM posts WHERE id =? AND username =?";
        try (Connection conn = source.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not exists");
        }
    }
}
