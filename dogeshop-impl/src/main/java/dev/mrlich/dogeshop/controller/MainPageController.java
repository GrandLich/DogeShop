package dev.mrlich.dogeshop.controller;

import dev.mrlich.dogeshop.auth.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final UserAuthentication authentication;

    @GetMapping("/")
    public ModelAndView mainPage(Model model) {
        return new ModelAndView("main", getAuthenticationModels());
    }

    @GetMapping("/cart")
    public ModelAndView cart(Model model) {
        if (!authentication.isLoggedIn()) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("cart", getAuthenticationModels());
    }

    @GetMapping("/lk")
    public ModelAndView lk(Model model) {
        if (!authentication.isLoggedIn()) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("lk", getAuthenticationModels());
    }

    @GetMapping("/login")
    public ModelAndView login(Model model) {
        if (authentication.isLoggedIn()) {
            return new ModelAndView("redirect:/lk");
        }
        return new ModelAndView("login", getAuthenticationModels());
    }

    @GetMapping("/reg")
    public ModelAndView reg(Model model) {
        if (authentication.isLoggedIn()) {
            return new ModelAndView("redirect:/lk");
        }
        return new ModelAndView("reg", getAuthenticationModels());
    }

    @GetMapping("/payment")
    public ModelAndView payment(Model model) {
        if(!authentication.isLoggedIn()) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("payment", getAuthenticationModels());
    }

    private Map<String, Object> getAuthenticationModels() {
        Map<String, Object> models = new HashMap<>();
        models.put("authed", authentication.isLoggedIn());
        if (authentication.isLoggedIn()) {
            models.put("balance", authentication.getCurrentAccount().getBalance());
            models.put("username", authentication.getCurrentAccount().getName());
        }
        return models;
    }

}
