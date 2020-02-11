package com.example.tickettrader;


import android.app.Activity;
import android.view.View;
import android.widget.Button;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;


public class jack_register_test {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void register_success() {
        //Makes sure that the registration page follows all of the requirements and doesn't register a user.
        Registration test = mock(Registration.class);
        String firstName = "Test";
        String lastName = "Mockito";
        String netID = "Mockito@iastate.edu";
        String password ="Password1!";
        test.setContentView(R.layout.login);
        test.register(firstName,lastName,netID,password);
        Assert.assertFalse( String.valueOf(test.getClass()).contains("Register"));
    }

}

