package jdbc;

import model.Echipa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.EchipaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EchipaDBRepository implements EchipaRepository {
    private final static Logger log = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public EchipaDBRepository(Properties prop) {
        log.info("Initializing CarsDBRepository with properties: {} ", prop);
        dbUtils = new JdbcUtils(prop);

    }

    @Override
    public void add(Echipa elem) {

        log.traceEntry(" parameters {}", elem);
        if (findById(elem.getId()).getId() != -1) {
            //throw log.throwing(new RuntimeException("Element already exists!!!"));
            log.warn("Element already exists: " + elem.getId());
        } else {

            Connection connection = dbUtils.getConnection();
            try {
                PreparedStatement statement =
                        connection.prepareStatement("INSERT INTO BILETE(id,nume, nr_membrii) values (?,?,?)");
                statement.setInt(1, elem.getId());
                statement.setString(2, elem.getNume());
                statement.setInt(3, elem.getNr_membrii());

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
                    connection.prepareStatement("delete from Echipe where id = ?");
            statement.setInt(1,integer);
            //in loc de primul semnul intrebarii punem id 1=?
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void update(Echipa elem) {
        log.traceEntry(" parameters {}", elem);

        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("update ECHIPE set nume = ?, nr_membrii = ? where id=?");
            statement.setString(1, elem.getNume());
            statement.setInt(2, elem.getNr_membrii());
            statement.setInt(3, elem.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Echipa findById(Integer id) {

        log.traceEntry(" parameters {}", id);
        ResultSet resultSet = null;
        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * from Echipe where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Echipa echipa = new Echipa();
                echipa.setId(resultSet.getInt(1));
                echipa.setNume(resultSet.getString(2));
                echipa.setNr_membrii(resultSet.getInt(3));
                return echipa;
            } else {
                Echipa echipa = new Echipa();
                echipa.setId(-1);
                return echipa;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Echipa echipa = new Echipa();
        echipa.setId(-1);
        return echipa;
    }



    @Override
    public List<Echipa> getAll() {
        log.traceEntry(" Get all");
        ResultSet resultSet = null;
        Connection connection = dbUtils.getConnection();
        List<Echipa> echipe = new ArrayList<>();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * from BILETE");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Echipa echipa = new Echipa();
                echipa.setId(resultSet.getInt(1));
                echipa.setNume(resultSet.getString(2));
                echipa.setNr_membrii(resultSet.getInt(3));
                echipe.add(echipa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return echipe;
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
