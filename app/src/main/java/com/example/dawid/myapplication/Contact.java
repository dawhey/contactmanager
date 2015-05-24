package com.example.dawid.myapplication;

/**
 * Created by Dawid on 2015-05-03.
 */
public class Contact {

    private String _name, _email, _phonenumber;

    public Contact(String name, String phonenumber, String email)
    {
        _name = name;
        _phonenumber = phonenumber;
        _email = email;
    }

    public String getName()
    {
        return _name;
    }

    public String getPhonenumber()
    {
        return _phonenumber;
    }

    public String getEmail()
    {
        return _email;
    }


}
