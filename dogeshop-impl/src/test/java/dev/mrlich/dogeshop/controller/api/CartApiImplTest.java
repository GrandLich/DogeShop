package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.dto.response.MessageResponse;
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
public class CartApiImplTest {

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
    private CartApiImpl cartApi;

    @Test
    void getCartContentsShouldReturnForbiddenIfUserNotAuthorized() {
        ResponseEntity<List<SkinDto>> actual = cartApi.getCartContents();

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void getCartContentsShouldReturnListOfSkinsOfCurrentUser() {
        when(authentication.getCurrentAccount()).thenReturn(new Account());
        when(authentication.isLoggedIn()).thenReturn(true);
        when(accountService.getSkinsInCart(any())).thenReturn(Collections.emptySet());
        when(mapper.mapAsList(anyIterable(), any())).thenReturn(Collections.emptyList());

        ResponseEntity<List<SkinDto>> actual = cartApi.getCartContents();

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.OK);
        assertNotNull(actual.getBody());
        assertEquals(actual.getBody().size(), 0);
    }

    @Test
    void addSkinToCartShouldReturnForbiddenIfUserNotAuthorized() {
        Long id = TestUtils.randomLong();
        ResponseEntity<MessageResponse> actual = cartApi.addSkinToCart(id);

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void addSkinToCartShouldReturnBadRequestIfSkinNotFound() {
        Long id = TestUtils.randomLong();
        when(authentication.isLoggedIn()).thenReturn(true);
        when(skinService.getSkin(id)).thenReturn(Optional.empty());

        ResponseEntity<MessageResponse> actual = cartApi.addSkinToCart(id);

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void addSkinToCartShouldCallAddSkinToCartMethodFromAccountServiceAndReturnOk() {
        Long id = TestUtils.randomLong();
        when(authentication.getCurrentAccount()).thenReturn(new Account());
        when(authentication.isLoggedIn()).thenReturn(true);
        when(skinService.getSkin(id)).thenReturn(Optional.of(generateSkin()));

        ResponseEntity<MessageResponse> actual = cartApi.addSkinToCart(id);

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.OK);
        verify(accountService).addSkinToCart(any(), any());
    }

    @Test
    void clearCartShouldReturnForbiddenIfUserNotAuthorized() {
        ResponseEntity<MessageResponse> actual = cartApi.clearCart();

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void clearCartShouldCallAccountServiceMethodAndReturnOk() {
        Account account = new Account();
        when(authentication.getCurrentAccount()).thenReturn(account);
        when(authentication.isLoggedIn()).thenReturn(true);

        ResponseEntity<MessageResponse> actual = cartApi.clearCart();

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.OK);
        verify(accountService).clearCart(account);
    }

    @Test
    void buyShouldReturnForbiddenIfUserNotAuthorized() {
        ResponseEntity<MessageResponse> actual = cartApi.buy();

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void buyShouldReturnNotEnoughMoneyIfUserDoesNotHaveMoneyForThisOperation() {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);

        when(authentication.getCurrentAccount()).thenReturn(account);
        when(authentication.isLoggedIn()).thenReturn(true);
        when(accountService.getSkinsInCart(account)).thenReturn(Collections.singleton(generateSkin()));

        ResponseEntity<MessageResponse> actual = cartApi.buy();

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals("Not enough money", actual.getBody().getMessage());
    }

    @Test
    void positiveBuyShouldWorkCorrectly() {
        Account account = new Account();
        account.setBalance(BigDecimal.TEN);

        when(authentication.getCurrentAccount()).thenReturn(account);
        when(authentication.isLoggedIn()).thenReturn(true);
        when(accountService.getSkinsInCart(account)).thenReturn(Collections.singleton(generateSkin()));

        ResponseEntity<MessageResponse> actual = cartApi.buy();

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNull(actual.getBody());

        verify(orderService).createOrder(any(), any());
        verify(accountService).addOrderToAccount(eq(account), any());
        verify(accountService).setBalance(account, new BigDecimal(9));
        verify(accountService).clearCart(account);
    }

    private Skin generateSkin() {
        Skin skin = new Skin();
        skin.setName(UUID.randomUUID().toString());
        skin.setPrice(BigDecimal.ONE);

        return skin;
    }

}
