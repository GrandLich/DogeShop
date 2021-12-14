package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.dto.response.MessageResponse;
import dev.mrlich.dogeshop.api.exception.ActionIsNotAllowedException;
import dev.mrlich.dogeshop.api.exception.EntityNotFoundException;
import dev.mrlich.dogeshop.api.exception.NotEnoughMoneyException;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.service.AccountService;
import dev.mrlich.dogeshop.service.OrderService;
import dev.mrlich.dogeshop.service.SkinService;
import dev.mrlich.dogeshop.util.test.TestUtils;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartResourceImplTest {

    @Mock
    private UserAuthentication authentication;
    @Mock
    private AccountService accountService;
    @Mock
    private SkinService skinService;
    @Mock
    private OrderService orderService;
    @Mock
    private MapperFacade mapper;

    @InjectMocks
    private CartResourceImpl cartApi;

    @Test
    void getCartContentsShouldThrowActionIsNotAllowedExceptionIfUserNotAuthorized() {
        assertThrows(ActionIsNotAllowedException.class, () -> cartApi.getCartContents());
    }

    @Test
    void getCartContentsShouldReturnListOfSkinsOfCurrentUser() {
        when(authentication.getAccount()).thenReturn(new Account());
        when(authentication.isLoggedIn()).thenReturn(true);
        when(accountService.getSkinsInCart(any())).thenReturn(Collections.emptySet());
        when(mapper.mapAsList(anyIterable(), any())).thenReturn(Collections.emptyList());

        List<SkinDto> actual = cartApi.getCartContents();

        assertNotNull(actual);
        assertEquals(actual.size(), 0);
    }

    @Test
    void addSkinToCartShouldThrowActionIsNotAllowedExceptionIfUserNotAuthorized() {
        Long id = TestUtils.randomLong();
        assertThrows(ActionIsNotAllowedException.class, () -> cartApi.addSkinToCart(id));
    }

    @Test
    void addSkinToCartShouldReturnBadRequestIfSkinNotFound() {
        Long id = TestUtils.randomLong();
        when(authentication.isLoggedIn()).thenReturn(true);
        when(skinService.getSkin(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartApi.addSkinToCart(id));
    }

    @Test
    void addSkinToCartShouldCallAddSkinToCartMethodFromAccountService() {
        Long id = TestUtils.randomLong();
        when(authentication.getAccount()).thenReturn(new Account());
        when(authentication.isLoggedIn()).thenReturn(true);
        when(skinService.getSkin(id)).thenReturn(Optional.of(generateSkin()));

        cartApi.addSkinToCart(id);

        verify(accountService).addSkinToCart(any(), any());
    }

    @Test
    void clearCartShouldThrowActionIsNotAllowedExceptionIfUserNotAuthorized() {
        assertThrows(ActionIsNotAllowedException.class, () -> cartApi.clearCart());
    }

    @Test
    void clearCartShouldCallAccountServiceMethodAndReturnOk() {
        Account account = new Account();
        when(authentication.getAccount()).thenReturn(account);
        when(authentication.isLoggedIn()).thenReturn(true);

        cartApi.clearCart();

        verify(accountService).clearCart(account.getId());
    }

    @Test
    void buyShouldThrowActionIsNotAllowedExceptionIfUserNotAuthorized() {
        assertThrows(ActionIsNotAllowedException.class, () -> cartApi.buy());
    }

    @Test
    void buyShouldReturnNotEnoughMoneyIfUserDoesNotHaveMoneyForThisOperation() {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);

        when(authentication.getAccount()).thenReturn(account);
        when(authentication.isLoggedIn()).thenReturn(true);
        when(accountService.getSkinsInCart(account.getId())).thenReturn(Collections.singleton(generateSkin()));

        assertThrows(NotEnoughMoneyException.class, () -> cartApi.buy());
    }

    @Test
    void positiveBuyShouldWorkCorrectly() {
        Account account = new Account();
        account.setBalance(BigDecimal.TEN);

        when(authentication.getAccount()).thenReturn(account);
        when(authentication.isLoggedIn()).thenReturn(true);
        when(accountService.getSkinsInCart(account.getId())).thenReturn(Collections.singleton(generateSkin()));

        cartApi.buy();

        verify(orderService).createOrder(any(), any());
        verify(accountService).addOrderToAccount(eq(account.getId()), any());
        verify(accountService).setBalance(account, new BigDecimal(9));
        verify(accountService).clearCart(account.getId());
    }

    private Skin generateSkin() {
        Skin skin = new Skin();
        skin.setName(UUID.randomUUID().toString());
        skin.setPrice(BigDecimal.ONE);

        return skin;
    }

}
