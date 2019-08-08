package com.telran.jdbcforum.repository;

import com.telran.jdbcforum.entity.CommentEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepoImpl implements CommentsRepository {

    @Autowired
    DataSource source;

    @Override
    public List<CommentEntity> getAllComments() {
        List<CommentEntity> res = new ArrayList<>();
        try (Connection connection = source.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM comments");
            while (result.next()) {
                CommentEntity entity = getCommentEntity(result);
                res.add(entity);
            }
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return res;

    }

    @NotNull
    private CommentEntity getCommentEntity(ResultSet set) throws SQLException {
        CommentEntity entity = new CommentEntity();
        entity.setId(set.getInt("id"));
        entity.setComment(set.getString("comment"));
        entity.setDateTime(set.getTimestamp("date_time"));
        entity.setPostId(set.getInt("post_id"));
        entity.setUserName(set.getString("userName"));
        return entity;
    }

    @Override
    public CommentEntity getCommentById(int id) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM comments WHERE id=?");
            ps.setString(1, String.valueOf(id));
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result.next()) {
                CommentEntity entity = getCommentEntity(result);
                return entity;
            }
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with id " + id + " is not exist");
    }

    @Override
    public void addComment(int postId, String email) {

    }

    @Override
    public void removeComment(int postId, int commentId, String email) {

    }

    @Override
    public void updateComment(int postId, CommentEntity commentEntity) {

    }
}

