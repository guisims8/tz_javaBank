package org.academiadecodigo.javabank;

import org.academiadecodigo.javabank.controller.Controller;
import org.academiadecodigo.javabank.controller.LoginController;
import org.academiadecodigo.javabank.services.AuthService;
import org.academiadecodigo.javabank.services.AuthServiceImpl;
import org.academiadecodigo.javabank.view.LoginView;
import org.academiadecodigo.javabank.view.View;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private LoginController loginController;
    private AuthServiceImpl authServiceImpl;
    private Controller nextController;
    View loginView;
    Controller anotherController;
    int fakeID = 123456;


    @Before
    public void setUp() {
        loginController = new LoginController();

        authServiceImpl = Mockito.mock(AuthServiceImpl.class);
        nextController = Mockito.mock(Controller.class);
        loginView = Mockito.mock(LoginView.class);

        loginController.setNextController(nextController);
        loginController.setAuthService(authServiceImpl);
        loginController.setView(loginView);
    }

    @Test
    public void init() {
        when(authServiceImpl.authenticate(fakeID)).thenReturn(false);
        loginController.onLogin(1);
        verify(loginView).show();
    }

    @Test
    public void onLogin() {
        //Auth successfull
        when(authServiceImpl.authenticate(fakeID)).thenReturn(true);
        loginController.onLogin(fakeID);

        verify(nextController).init();
        verify(authServiceImpl).authenticate(fakeID);
        verify(loginView, never()).show();

        assertFalse(loginController.isAuthFailed());

    }

    @Test
    public void onLoginFail() {
        when(authServiceImpl.authenticate(10)).thenReturn(false);
        loginController.onLogin(10);

        verify(nextController, never()).init();
        verify(authServiceImpl).authenticate(10);
        verify(loginView).show();

        assertTrue(loginController.isAuthFailed());

    }


    @Test
    public void isAuthFailed() {
        when(authServiceImpl.authenticate(fakeID)).thenReturn(true);
        loginController.onLogin(fakeID);
        assertFalse(loginController.isAuthFailed());

        when(authServiceImpl.authenticate(fakeID)).thenReturn(false);
        loginController.onLogin(fakeID);
        assertTrue(loginController.isAuthFailed());
    }

}