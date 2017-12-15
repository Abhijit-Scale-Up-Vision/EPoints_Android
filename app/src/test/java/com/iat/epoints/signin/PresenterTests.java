package com.iat.epoints.signin;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ramya.
 */

public class PresenterTests {

    SignInActivityMVP.Model signInModel;
    SignInActivityMVP.View signInView;
    SignInActivityPresenter signInActivityPresenter;
    User user;
    Token token;

    @Before
    public void setUp(){
        signInModel = mock(SignInActivityMVP.Model.class);

        signInView = mock(SignInActivityMVP.View.class);

        signInActivityPresenter = new SignInActivityPresenter(signInModel);
        signInActivityPresenter.setView(signInView);

        user = new User("fox@gmail.com","mulder");

        token = new Token("ssccszcs");

    }

    @Test
    public void shouldShowErrorWhenPasswordIsNotEntered(){
        when(signInModel.getUser ()).thenReturn(null);

        signInActivityPresenter.getCurrentUser();

        //verify model interactions
        verify(signInModel, times(1)).getUser();

        //verify view interactions

        verify(signInView, never()).setEmail("fox@gmail.com");
        verify(signInView, never()).setPassword("");
        verify(signInView, times(0)).showUserPasswordIsWrong();

    }

    @Test
    public void shouldShowErrorIfFieldsAreEmpty(){
        when(signInModel.getUser()).thenReturn(null);

        signInActivityPresenter.getCurrentUser();

        verify(signInModel,times(1)).getUser();

        verify(signInView, never()).setEmail("");
        verify(signInView, never()).setPassword("");
        verify(signInView, times(0)).showEmailAndPAsswordAreEmpty();

    }

    @Test
    public void testSignInUser_Email(){
        when(signInModel.getUser()).thenReturn(user);

        signInActivityPresenter.getCurrentUser();

        verify(signInModel,times(1)).getUser();

        verify(signInView, times(0)).signInUser();
        //verify(signInView, times(0)).signInSuccess(token.getToken());

    }

}
