package cinema.service;

import cinema.model.Ticket;
import cinema.store.TicketStore;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {
    private final TicketStore ticketStore;

    public TicketService(TicketStore ticketStore) {
        this.ticketStore = ticketStore;
    }

    public Optional<Ticket> add(Ticket ticket) {
        return ticketStore.add(ticket);
    }
}
