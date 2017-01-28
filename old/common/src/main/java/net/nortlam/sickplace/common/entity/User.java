/*
 * 
 * Copyright 2014 Mauricio "Maltron" Leal <maltron@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package net.nortlam.sickplace.common.entity;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonObject;
import net.nortlam.sickplace.common.JSON;
import net.nortlam.sickplace.common.PropertySupport;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
public class User extends PropertySupport implements Serializable, JSON {
    
    public static final String PROPERTY_ID = "ID";
    private long ID;
    
    public static final String PROPERTY_EMAIL = "email";
    private String email;
    
    public static final String PROPERTY_PASSWORD = "password";
    private String password;
    
    private PropertyChangeSupport pss;

    public User() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        long oldValue = this.ID;
        this.ID = ID;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_ID, oldValue, this.ID);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldValue = this.email;
        this.email = email;
        getPropertyChangeSupport()
                .firePropertyChange(PROPERTY_EMAIL, oldValue, this.email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldValue = this.password;
        this.password = password;
        getPropertyChangeSupport()
                .firePropertyChange(PROPERTY_PASSWORD, oldValue, this.password);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 89 * hash + Objects.hashCode(this.email);
        hash = 89 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<User ID=\"").append(ID).append("\">");
        builder.append("<Email>").append(email != null ? email : "NULL")
                .append("</Email>");
        builder.append("<Password>").append(password != null ? password : "NULL")
                .append("</Password>");
        builder.append("</User>");
        
        return builder.toString();
    }
    
    // JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON 
    //   JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON 
    
    @Override
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
           .add(PROPERTY_ID, getID())
           .add(PROPERTY_EMAIL, getEmail())
           .add(PROPERTY_PASSWORD, getPassword())
        .build();
    }

    // PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER 
    //  PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER 

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        if(pss == null) pss = new PropertyChangeSupport(this);
        
        return pss;
    }
}
