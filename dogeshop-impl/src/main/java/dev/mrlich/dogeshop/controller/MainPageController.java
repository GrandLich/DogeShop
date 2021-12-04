package dev.mrlich.dogeshop.controller;

import dev.mrlich.dogeshop.api.model.Skin;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.service.SkinService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final UserAuthentication authentication;
    private final SkinService skinService;
    private final MapperFacade mapperFacade;

    @GetMapping("/")
    public ModelAndView mainPage(Model model) {
        return new ModelAndView("main", mergeMaps(getAuthenticationModels(), getMainPageSkins()));
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

    private Map<String,Object> getMainPageSkins() {
        Map<String, Object> models = new HashMap<>();
        List<Skin> skins = mapperFacade.mapAsList(skinService.getAll(), Skin.class);
        models.put("skins", skins);
        return models;
    }

    @SafeVarargs
    private <K,V> Map<K,V> mergeMaps(Map<K,V>... maps) {
        Map<K,V> resultMap = new HashMap<>();
        for (Map<K, V> map : maps) {
            resultMap.putAll(map);
        }
        return resultMap;
    }

}
