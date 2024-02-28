package com.example.coffee.Services;

import com.example.coffee.Entity.Contact;
import com.example.coffee.Entity.User;
import com.example.coffee.Pojo.ContactPojo;
import com.example.coffee.Pojo.UserPojo;

import java.util.List;

public interface UserService {


    String save(UserPojo userPojo);

    String update(UserPojo userPojo);

    User getById(Integer id);

//    contact Page
String submitMsg(ContactPojo contactPojo);
    List<Contact> fetchAllContact();
    List<User> fetchAllUser();
    void deleteCustomer(Integer id);
    void deleteContact(Integer id);

    User findByEmail(String email);

    String updateResetPassword(String email);

    void processPasswordResetRequest(String email);

    void sendEmail();


}
