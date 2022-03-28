package jdbc;

import model.Bilet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.BiletRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class BiletDBRepository implements BiletRepository {
    private final static Logger log = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public BiletDBRepository(Properties prop) {
        log.info("Initializing CarsDBRepository with properties: {} ", prop);
        dbUtils = new JdbcUtils(prop);

    }

    @Override
    public void add(Bilet elem) {

        log.traceEntry(" parameters {}", elem);
        if (findById(elem.getId()).getId() != -1) {
            //throw log.throwing(new RuntimeException("Element already exists!!!"));
            log.warn("Element already exists: " + elem.getId());
        } else {

            Connection connection = dbUtils.getConnection();
            try {
                PreparedStatement statement =
                        connection.prepareStatement("INSERT INTO BILETE(id,nr_loc,nr_rand,pret) values (?,?,?,?)");
                statement.setInt(1, elem.getId());
                statement.setInt(2, elem.getNr_loc());
                statement.setInt(3, elem.getNr_rand());
                statement.setInt(4, elem.getPret());
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
                    connection.prepareStatement("delete from BILETE where id = ?");
            statement.setInt(1, integer);
            //in loc de primul semnul intrebarii punem id 1=?
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void update(Bilet elem) {
        log.traceEntry(" parameters {}", elem);

        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("update BILETE set nr_loc = ?, nr_rand = ?, pret = ? where id = ?");
            statement.setInt(1, elem.getNr_loc());
            statement.setInt(2, elem.getNr_rand());
            statement.setInt(3, elem.getPret());
            statement.setInt(4, elem.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Bilet findById(Integer id) {

        log.traceEntry(" parameters {}", id);
        ResultSet resultSet = null;
        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * from BILETE where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Bilet bilet = new Bilet();
                bilet.setId(resultSet.getInt(1));
                bilet.setNr_loc(resultSet.getInt(2));
                bilet.setNr_rand(resultSet.getInt(3));
                bilet.setPret(resultSet.getInt(4));

                resultSet.close();

                return bilet;
            } else {
                Bilet bilet = new Bilet();
                bilet.setId(-1);
                return bilet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Bilet bilet = new Bilet();
        bilet.setId(-1);
        return bilet;
    }


    @Override
    public List<Bilet> getAll() {
        log.traceEntry(" Get all");
        ResultSet resultSet = null;
        Connection connection = dbUtils.getConnection();
        List<Bilet> bilete = new ArrayList<>();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * from BILETE");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Bilet bilet = new Bilet();
                bilet.setId(resultSet.getInt(1));
                bilet.setNr_loc(resultSet.getInt(2));
                bilet.setNr_rand(resultSet.getInt(3));
                bilet.setPret(resultSet.getInt(4));
                bilete.add(bilet);
            }
            if (resultSet != null) {
                resultSet.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bilete;
    }

}