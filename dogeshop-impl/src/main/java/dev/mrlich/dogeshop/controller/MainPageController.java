package dev.mrlich.dogeshop.controller;

import dev.mrlich.dogeshop.api.dto.OrderDto;
import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.Skin;
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
            models.put("balance", authentication.getAccount().getBalance());
            models.put("username", authentication.getAccount().getName());
        }
        return models;
    }

    private Map<String, Object> getMainPageSkins() {
        Map<String, Object> models = new HashMap<>();
        List<SkinDto> skinDtos = mapperFacade.mapAsList(skinService.getAll(), SkinDto.class);
        models.put("skins", skinDtos);
        return models;
    }

    private Map<String, Object> getCartSkins() {
        Map<String, Object> models = new HashMap<>();
        if (!authentication.isLoggedIn()) {
            return models;
        }
        models.put("cartSkins", mapperFacade.mapAsList(accountService.getSkinsInCart(authentication.getAccount().getId()), SkinDto.class));
        BigDecimal cartSkinsTotalPrice = accountService.getSkinsInCart(authentication.getAccount().getId()).stream()
                .map(Skin::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        models.put("cartSkinsTotal",cartSkinsTotalPrice);
        return models;
    }

    private Map<String, Object> getOrderHistory() {
        Map<String, Object> models = new HashMap<>();
        if (!authentication.isLoggedIn()) {
            return models;
        }
        models.put("orders", mapperFacade.mapAsList(accountService.getOrders(authentication.getAccount().getId()), OrderDto.class));
        return models;
    }


}
