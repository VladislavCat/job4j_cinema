package cinema.store;

import cinema.Main;
import cinema.model.Session;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class SessionDBStore {
    private final BasicDataSource pool;
    private final Logger logger = LoggerFactory.getLogger(SessionDBStore.class);
    private static final byte[] STOCKPICTURE = getStockPicture();
    private final String findAllSession = "select * from sessions";
    private final String findById = findAllSession + " where id = ?";
    private int i = 1;

    public SessionDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public static byte[] getStockPicture() {
        byte[] rsl = {};
        try (FileInputStream fis = new FileInputStream("stock_picture.png")) {
            rsl = fis.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public List<Session> getAllSession() {
        List<Session> rsl = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(findAllSession)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        rsl.add(createSession(rs));
                    }
                }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return rsl;
    }

    public Session findById(int id) {
        try (Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement(findById)) {
                ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createSession(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Session createSession(ResultSet rs) throws SQLException {
        return new Session(rs.getInt(1), rs.getString(2), rs.getBytes(3),
                rs.getString(4), rs.getDate(5).toString());
    }

    private InputStream getImage() throws FileNotFoundException {
        return new FileInputStream("picture/" + i++ + ".png");
    }

    public void addNewSession(List<Session> list) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("INSERT INTO sessions(name, picture, "
                + "description_film, date_film) values(?, ?, ?, ?)")) {
            for (Session session : list) {
                ps.setString(1, session.getName());
                ps.setBinaryStream(2, getImage());
                ps.setString(3, session.getDescription());
                long date = new java.util.Date().getTime();
                ps.setDate(4, new Date(date));
                ps.execute();
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

