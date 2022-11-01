package cinema.controller;

import cinema.model.Session;
import cinema.service.SessionService;
import cinema.util.UserName;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class SessionController {
    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @GetMapping("/session")
    public String session(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("sessions", service.getAllSession());
        return "session";
    }

    @GetMapping("/photoSession/{sessionId}")
    public ResponseEntity<Resource> download(@PathVariable("sessionId") Integer sessionId) {
        Session session = service.findById(sessionId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(session.getPicture().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(session.getPicture()));
    }
}
