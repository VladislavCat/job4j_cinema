package cinema.controller;

import cinema.model.User;
import cinema.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
 @Controller
 public class UserController {
     private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/formRegistration")
        public String createUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
            model.addAttribute("user", new User("email", "username", "phone", "password"));
            model.addAttribute("fail", fail != null);
            return "registration";
        }

        @PostMapping("/registration")
        public String registration(Model model, @ModelAttribute User user) {
            Optional<User> regUser = userService.add(user);
            if (regUser.isEmpty()) {
                model.addAttribute("message", "Пользователь с такой почтой уже существует");
                return "redirect:/formRegistration?fail=true";
            }
            return "redirect:/session";
        }

        @GetMapping("/loginPage")
        public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
            model.addAttribute("fail", fail != null);
            return "login";
        }

        @PostMapping("/login")
        public String login(@ModelAttribute User user, HttpServletRequest req) {
            Optional<User> userDb = userService.findUserByEmailAndPassword(
                    user.getEmail(), user.getPassword()
            );
            if (userDb.isEmpty()) {
                return "redirect:/loginPage?fail=true";
            }
            HttpSession session = req.getSession();
            session.setAttribute("user", userDb.get());
            return "redirect:/session";
        }

        @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.invalidate();
            return "redirect:/session";
        }
 }

