package com.desire323.user.DAO;

import com.desire323.user.entity.User;
import com.desire323.user.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDAOMySQL implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDAOMySQL(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM user WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        try{
            User user = jdbcTemplate.queryForObject(sql, params, new UserMapper());

            return Optional.of(user);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = :email LIMIT 1";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        try{
            User user = jdbcTemplate.queryForObject(sql,
                    params,
                    new UserMapper());

            return Optional.of(user);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    public void save(User user) {
        String sql = "INSERT INTO user (firstname, lastname, email, password, role) " +
                "VALUES (:firstname, :lastname, :email, :password, :role)";
        Map<String, Object> params = new HashMap<>();
        params.put("firstname", user.getFirstname());
        params.put("lastname", user.getLastname());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("role", user.getRole().name());
        jdbcTemplate.update(sql, params);
    }

    public void update(User user) {
        String sql = "UPDATE user SET firstname = :firstname, lastname = :lastname, " +
                "email = :email, password = :password, role = :role WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("firstname", user.getFirstname());
        params.put("lastname", user.getLastname());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("role", user.getRole().name());
        params.put("id", user.getId());
        jdbcTemplate.update(sql, params);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM user WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcTemplate.update(sql, params);
    }

    private static class UserMapper extends BeanPropertyRowMapper<User> {
        public UserMapper() {
            super(User.class);
        }
    }
}
