package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.User;
import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class UserRepositoryImpl implements UserRepository {
    private UnitOfWork unitOfWork;

    public UserRepositoryImpl(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public User findById(int id) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    select * from public.user
                    where id = ?
                """))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            while(resultSet.next())
            {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
            }
            return user;
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

    @Override
    public void addUser(User user) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                insert into public.user (username, email, password)
                values (?, ?, ?)
            """))
        {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Insert nicht erfolgreich", e);
        }
    }


    @Override
    public Collection<User> findAllUsers() {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    select * from user
                """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> userRows = new ArrayList<>();
            while(resultSet.next())
            {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
                userRows.add(user);
            }

            return userRows;
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }
}
