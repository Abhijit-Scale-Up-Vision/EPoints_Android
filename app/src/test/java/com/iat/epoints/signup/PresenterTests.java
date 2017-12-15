package com.iat.epoints.signup;


import com.iat.epoints.signup.SignUpActivityMVP;
import com.iat.epoints.signup.SignUpActivityPresenter;
import com.iat.epoints.signup.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

public class PresenterTests {

    SignUpActivityMVP.Model mockLoginModel;
    SignUpActivityMVP.View mockView;
    SignUpActivityPresenter presenter;
    User user;

    @Before
    public  void setup(){

        mockLoginModel = mock(SignUpActivityMVP.Model.class);

        user = new User("Fox", "Mulder", "fox@gmail.com", "mulder");

        //when(mockLoginModel.getUser()).thenReturn(user);

        mockView = mock(SignUpActivityMVP.View.class);

        presenter = new SignUpActivityPresenter(mockLoginModel);

        presenter.setView(mockView);

    }


   /* @Test
    public void noInteractionWithView(){

        presenter.getCurrentUser();

        verifyZeroInteractions(mockView);

    }*/

    @Test
    public void loadTheUserFromTheRepositoryWhenValidUserIsPresent() {

        when(mockLoginModel.getUser()).thenReturn(user);

        presenter.getCurrentUser();

        //verify model interactions
        verify(mockLoginModel, times(1)).getUser();

        //verify view interactions
        verify(mockView, times(1)).setFirstName("Fox");
        verify(mockView, times(1)).setLastName("Mulder");
        verify(mockView, times(1)).setEmail("fox@gmail.com");
        verify(mockView, times(1)).setPassword("mulder");
        verify(mockView, never()).showUserNotAvailable();

    }

    @Test
    public void shouldShowErrorMessageWhenUserIsNull() {

        when(mockLoginModel.getUser()).thenReturn(null);

        presenter.getCurrentUser();

        //verify model interactions
        verify(mockLoginModel, times(1)).getUser();

        //verify view interactions
        verify(mockView, never()).setFirstName("Fox");
        verify(mockView, never()).setLastName("Mulder");
        verify(mockView, never()).setEmail("fox@gmail.com");
        verify(mockView, never()).setPassword("mulder");
        verify(mockView, times(1)).showUserNotAvailable();

    }

    @Test
    public void shouldCreateErrorMessageIfFieldAreEmpty() {

        // Set up the view mock
        when(mockView.getFirstName()).thenReturn(""); // empty string

        presenter.saveUser();

        verify(mockView, times(1)).getFirstName();
        verify(mockView, never()).getLastName();
        verify(mockView, never()).getEmail();
        verify(mockView, never()).getPassword();
        verify(mockView, times(1)).showInputError();

        // Now tell mockView to return a value for first name and an empty last name
        when(mockView.getFirstName()).thenReturn("Dana");
        when(mockView.getLastName()).thenReturn("");
        when(mockView.getEmail()).thenReturn("");
        when(mockView.getPassword()).thenReturn("");

        presenter.saveUser();

        verify(mockView, times(2)).getFirstName(); // Called two times now, once before, and once now
        verify(mockView, times(1)).getLastName();  // Only called once
        verify(mockView, times(0)).getEmail(); // Called two times now, once before, and once now
        verify(mockView, times(0)).getPassword();  // Only called once
        verify(mockView, times(2)).showInputError(); // Called two times now, once before and once now
    }

    @Test
    public void shouldBeAbleToSaveAValidUser() {

        when(mockView.getFirstName()).thenReturn("Dana");
        when(mockView.getLastName()).thenReturn("Scully");
        when(mockView.getEmail()).thenReturn("dana@gmail.com");
        when(mockView.getPassword()).thenReturn("scully");

        presenter.saveUser();

        // Called two more times in the saveUser call.
        verify(mockView, times(2)).getFirstName();
        verify(mockView, times(2)).getLastName();
        verify(mockView, times(1)).getEmail();
        verify(mockView, times(1)).getPassword();

        // Make sure the repository saved the user
        verify(mockLoginModel, times(1)).createUser("Dana", "Scully", "dana@gmail.com", "scully");

        // Make sure that the view showed the user saved message
        verify(mockView, times(1)).showUserSavedMessage();
    }

}
