package kulish.webapp.controllers;

import kulish.webapp.models.User;
import kulish.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        List<User> users = userService.getListUsers();
        model.addAttribute("users", users);
        return "/admin";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("user", userService.loadUserByUsername(username));
        return "/user";
    }

    @GetMapping("/admin/saveuser")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", userService.getRoles());
        return "saveuser";
    }

    @PostMapping("/admin/saveuser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/updateuser/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("listRoles", userService.getRoles());
        return "updateuser";
    }

    @PostMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
