package jdbc;

import model.Meci;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.MeciRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MeciDBRepository implements MeciRepository {
    private final static Logger log = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public MeciDBRepository(Properties prop) {
        log.info("Initializing ConcursDBRepository with properties: {} ", prop);
        dbUtils = new JdbcUtils(prop);

    }

    @Override
    public void add(Meci elem) {

        log.traceEntry(" parameters {}", elem);
        if (findById(elem.getId()).getId() != -1) {
            //throw log.throwing(new RuntimeException("Element already exists!!!"));
            log.warn("Element already exists: " + elem.getId());
        } else {

            Connection connection = dbUtils.getConnection();
            try {
                PreparedStatement statement =
                        connection.prepareStatement("INSERT INTO MECIURI(id,locuri_disponibile,echipa1,echipa2,etapa) values (?,?,?,?,?)");
                statement.setInt(1, elem.getId());
                statement.setInt(2, elem.getLocuri_disponibile());
                statement.setString(3, elem.getEchipa1());
                statement.setString(4, elem.getEchipa2());
                statement.setString(5, elem.getEtapa());
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
                    connection.prepareStatement("delete from MECIURI where id = ?");
            statement.setInt(1, integer);
            //in loc de primul semnul intrebarii punem id 1=?
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void update(Meci elem) {
        log.traceEntry(" parameters {}", elem);

        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("update MECIURI set locuri_disponibile = ?,echipa1 = ?,echipa2 = ?,etapa = ? where id = ?");
            statement.setInt(1, elem.getLocuri_disponibile());
            statement.setString(2, elem.getEchipa1());
            statement.setString(3, elem.getEchipa2());
            statement.setString(4, elem.getEtapa());
            statement.setInt(5, elem.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Meci findById(Integer id) {

        log.traceEntry(" parameters {}", id);
        ResultSet resultSet = null;
        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * from MECIURI where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Meci meci = new Meci();
                meci.setId(resultSet.getInt(1));
                meci.setLocuri_disponibile(resultSet.getInt(2));
                meci.setEchipa1(resultSet.getString(3));
                meci.setEchipa2(resultSet.getString(4));
                meci.setEtapa(resultSet.getString(5));

                return meci;
            } else {
                Meci meci = new Meci();
                meci.setId(-1);
                return meci;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Meci meci = new Meci();
        meci.setId(-1);
        return meci;
    }


    @Override
    public List<Meci> getAll() {
        log.traceEntry(" Get all");
        ResultSet resultSet = null;
        Connection connection = dbUtils.getConnection();
        List<Meci> concursuri = new ArrayList<>();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * from MECIURI");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Meci meci = new Meci();
                meci.setId(resultSet.getInt(1));
                meci.setLocuri_disponibile(resultSet.getInt(2));
                meci.setEchipa1(resultSet.getString(3));
                meci.setEchipa2(resultSet.getString(4));
                meci.setEtapa(resultSet.getString(5));
                concursuri.add(meci);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return concursuri;
    }

}
