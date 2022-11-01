package cinema.controller;

import cinema.model.Ticket;
import cinema.model.User;
import cinema.service.SessionService;
import cinema.service.TicketService;
import cinema.util.UserName;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class TicketController {
    private final SessionService sessionService;
    private final TicketService ticketService;
    private final int[] rowCount = new int[]{1, 2, 3, 4, 5};
    private final int[] columnCount = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public TicketController(SessionService sessionService, TicketService ticketService) {
        this.sessionService = sessionService;
        this.ticketService = ticketService;
    }

    @GetMapping("/selectSeat/{filmId}/{row}")
    public String selectSeat(Model model, @PathVariable("filmId") int id,  @PathVariable("row") int row, HttpSession session) {
        UserName.userSessionSetName(model, session);
        model.addAttribute("film", sessionService.findById(id));
        model.addAttribute("columnArr", columnCount);
        model.addAttribute("row", row);
        return "selectSeat";
    }

    @GetMapping("/selectRow/{filmId}")
    public String selectRow(Model model, @PathVariable("filmId") int id, HttpSession session) {
        UserName.userSessionSetName(model, session);
        model.addAttribute("film", sessionService.findById(id));
        model.addAttribute("rowArr", rowCount);
        return "selectRow";
    }

        @GetMapping("/payment/{filmId}/{row}/{column}")
    public String paymentTicket(Model model, @PathVariable("filmId") int id,
                                @PathVariable("row") int row, @PathVariable("column") int column,
                                HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        System.out.println(user);
        model.addAttribute("user", user);
        model.addAttribute("film", sessionService.findById(id));
        model.addAttribute("row", row);
        model.addAttribute("column", column);
        return "payment";
    }

    @PostMapping("/createTicket")
    public String createTicket(Model model, @ModelAttribute Ticket ticket) {
        Optional<Ticket> addTicket = ticketService.add(ticket);
        if (addTicket.isEmpty()) {
            model.addAttribute("message", "Это место уже забронировано.");
            return "redirect:/hall?fail=true";
        }
        return "redirect:/session";
    }
}
