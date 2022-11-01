package cinema.service;

import cinema.model.Session;
import cinema.store.SessionDBStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    private final SessionDBStore sds;

    public SessionService(SessionDBStore sds) {
        this.sds = sds;
    }

    public List<Session> getAllSession() {
        return sds.getAllSession();
    }

    public Session findById(int id) {
        return sds.findById(id);
    }
}
