package dev.mrlich.dogeshop.controller;

import dev.mrlich.dogeshop.api.model.Order;
import dev.mrlich.dogeshop.api.model.Skin;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.SkinEntity;
import dev.mrlich.dogeshop.service.AccountService;
import dev.mrlich.dogeshop.service.OrderService;
import dev.mrlich.dogeshop.service.SkinService;
import dev.mrlich.dogeshop.util.MapUtil;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final UserAuthentication authentication;
    private final SkinService skinService;
    private final AccountService accountService;
    private final OrderService orderService;
    private final MapperFacade mapperFacade;

    @GetMapping("/")
    public ModelAndView mainPage(Model model) {
        return new ModelAndView("main", MapUtil.mergeMaps(getAuthenticationModels(), getMainPageSkins(), getCartSkins()));
    }

    @GetMapping("/cart")
    public ModelAndView cart(Model model) {
        if (!authentication.isLoggedIn()) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("cart", MapUtil.mergeMaps(getAuthenticationModels(), getCartSkins()));
    }

    @GetMapping("/lk")
    public ModelAndView lk(Model model) {
        if (!authentication.isLoggedIn()) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("lk", MapUtil.mergeMaps(getAuthenticationModels(), getOrderHistory()));
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
        if (!authentication.isLoggedIn()) {
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

    private Map<String, Object> getMainPageSkins() {
        Map<String, Object> models = new HashMap<>();
        List<Skin> skins = mapperFacade.mapAsList(skinService.getAll(), Skin.class);
        models.put("skins", skins);
        return models;
    }

    private Map<String, Object> getCartSkins() {
        Map<String, Object> models = new HashMap<>();
        if (!authentication.isLoggedIn()) {
            return models;
        }
        models.put("cartSkins", mapperFacade.mapAsList(accountService.getSkinsInCart(authentication.getCurrentAccount()), Skin.class));
        BigDecimal cartSkinsTotalPrice = accountService.getSkinsInCart(authentication.getCurrentAccount()).stream()
                .map(SkinEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        models.put("cartSkinsTotal",cartSkinsTotalPrice);
        return models;
    }

    private Map<String, Object> getOrderHistory() {
        Map<String, Object> models = new HashMap<>();
        if (!authentication.isLoggedIn()) {
            return models;
        }
        models.put("orders", mapperFacade.mapAsList(orderService.getOrders(authentication.getCurrentAccount()), Order.class));
        return models;
    }


}
