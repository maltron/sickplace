package net.nortlam.usermanagement.entity;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
public class User implements Serializable {
    
    public static final String COLLECTION = "users";

    private static final Logger LOG = Logger.getLogger(User.class.getName());
    
    public static final String TAG_EMAIL = "email";
    private String email;
    
    public static final String TAG_FIRSTNAME = "firstName";
    private String firstName;
    
    public static final String TAG_LASTNAME = "lastName";
    private String lastName;
    
    public static final String TAG_PASSWORD = "password";
    private String password;
    
    private Role role;
    
    public User() {
        LOG.log(Level.INFO, ">>> User()");
    }

    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
    
    public User(Document document) {
        this.email = document.getString(TAG_EMAIL);
        this.firstName = document.getString(TAG_FIRSTNAME);
        this.lastName = document.getString(TAG_LASTNAME);
        this.password = document.getString(TAG_PASSWORD);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return toDocument().toJson();
    }
    
    public Document toDocument() {
        Document document = new Document();
        
        if(this.email != null) document.append(TAG_EMAIL, this.email);
        if(this.firstName != null) document.append(TAG_FIRSTNAME, this.firstName);
        if(this.lastName != null) document.append(TAG_LASTNAME, this.lastName);
        if(this.password != null) document.append(TAG_PASSWORD, this.password);
        
        return document;
    }
}
