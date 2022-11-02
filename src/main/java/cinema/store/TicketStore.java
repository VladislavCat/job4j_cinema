package cinema.store;

import cinema.model.Ticket;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;
@Repository
public class TicketStore {
    private final BasicDataSource pool;
    private final String insert = "insert into tickets(session_id, pos_row, cell, user_id) values(?, ?, ?, ?)";
    private final Logger logger = LoggerFactory.getLogger(TicketStore.class);

    public TicketStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getPosRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                    rsl = Optional.of(ticket);
                }
            }
        } catch (SQLException e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }
}
