package com.iat.epoints.landing;


import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Abhijit.
 */

public class PresenterTests {
    LandingActivityMVP.View landingActivityView;

    LandingActivityMVP.Model landingActivityModel;
    LandingActivityPresenter landingActivityPresenter;
    User user;

/*
    ButtonClicked buttonClicked;
*/
    @Before

    public void setUp(){

        landingActivityModel = mock(LandingActivityMVP.Model.class);

        user = new User(true,false);

        landingActivityView = mock(LandingActivityMVP.View.class);

        landingActivityPresenter = new LandingActivityPresenter(landingActivityModel);

        landingActivityPresenter.setView(landingActivityView);


    }

    @Test

    public void whenUserClickOnCreateEmailIdItShouldDirectToSignupScreen(){

        when(landingActivityModel.getUser()).thenReturn(user);

        landingActivityPresenter.getCurrentUser();

        //verify view interactions
        verify(landingActivityView, times(1)).gotoSignUp();

    }
   /* @Test

    public void whenUserClickOnCreateEmailIdItShouldDirectToSigninScreen(){
        when(landingActivityModel.getUser()).thenReturn(userSignin);
    }*/


}
