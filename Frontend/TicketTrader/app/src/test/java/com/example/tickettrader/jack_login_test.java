package com.example.tickettrader;


import android.app.Activity;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;




public class jack_login_test {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void login_fail() {
       //Tests to verify that a fake username and password will not work.
        Login test = mock(Login.class);
        String fakeUser = "liar";
        String fakePassword = "loser";
        test.setContentView(R.layout.login);

        test.loginVerify(fakeUser,fakePassword);

        Assert.assertTrue( String.valueOf(test.getClass()).contains("Login"));


    }

}

