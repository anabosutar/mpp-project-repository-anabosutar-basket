package jdbc;


import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDBRepository implements UserRepository {
    private final static Logger log = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public UserDBRepository(Properties prop) {
        log.info("Initializing CarsDBRepository with properties: {} ", prop);
        dbUtils = new JdbcUtils(prop);

    }

    @Override
    public void add(User elem) {

        log.traceEntry(" parameters {}", elem);
        if (findById(elem.getId()).getId() != -1) {
            //throw log.throwing(new RuntimeException("Element already exists!!!"));
            log.warn("Element already exists: " + elem.getId());
        } else {

            Connection connection = dbUtils.getConnection();
            try {
                PreparedStatement statement =
                        connection.prepareStatement("INSERT INTO Useri(id,username,password) values (?,?,?)");
                statement.setInt(1, elem.getId());
                statement.setString(2, elem.getUsername());
                statement.setString(3, elem.getPassword());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


//        if(elem.containsKey(elem.getId()))
//        {
//            throw log.throwing(new RuntimeException("Element already exists!!!"));
//            //throw new RuntimeException("Element already exists!!!");
//        }
//        else
//            elem.put(elem.getId(),elem);
//        log.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        log.traceEntry(" parameters {}", integer);

        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("delete from USERI where id = ?");
            statement.setInt(1,integer);
            //in loc de primul semnul intrebarii punem id 1=?
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void update(User elem) {
        log.traceEntry(" parameters {}", elem);

        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("update USERI set id = ?,username = ?,password = ? where id = ?");
            statement.setString(1, elem.getUsername());
            statement.setString(2, elem.getPassword());
            statement.setInt(4, elem.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User findById(Integer id) {

        log.traceEntry(" parameters {}", id);
        ResultSet resultSet = null;
        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * from USERI where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));

                return user;
            } else {
                User user = new User();
                user.setId(-1);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setId(-1);
        return user;
    }



    @Override
    public List<User> getAll() {
        log.traceEntry(" Get all");
        ResultSet resultSet = null;
        Connection connection = dbUtils.getConnection();
        List<User> useri = new ArrayList<>();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * from USERI");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                useri.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return useri;
    }

//    public void add(T el) {
//        log.traceEntry(" parameters {}",el);
//        if(elem.containsKey(el.getId()))
//        {
//            throw log.throwing(new RuntimeException("Element already exists!!!"));
//            //throw new RuntimeException("Element already exists!!!");
//        }
//        else
//            elem.put(el.getId(),el);
//        log.traceExit();
//    }
//
//
//    public void delete(ID id) {
//        log.traceEntry("{}",id);
//        if(elem.containsKey(id))
//            elem.remove(id);
//        log.traceExit();
//    }
//
//
//    public void update(T el, ID id) {
//        log.traceEntry("{}, {}",el,id );
//        if(elem.containsKey(id))
//            elem.put(el.getId(),el);
//        else
//            throw log.throwing(new RuntimeException("Element doesn’t exist"));
//        log.traceExit();
//    }
//
//
//    public T findById(ID id) {
//        log.traceEntry("{}",id);
//        if(elem.containsKey(id))
//
//            return log.traceExit(elem.get(id));
//            //  return elem.get(id);
//
//        else {
//
//            throw log.throwing(new RuntimeException("Element doesn't exist"));
//            //throw new RuntimeException("Element doesn't exist");
//        }
//    }
//
//
//    public Iterable<T> findAll() {
//        log.traceEntry();
//        return log.traceExit(elem.values());
//    }
//
//
//    @Override
//    public Collection<T> getAll() {
//        log.traceEntry();
//        return log.traceExit(elem.values());
//    }
}
