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
public class Patient extends PropertySupport implements Serializable, JSON {
    
    private PropertyChangeSupport pss;
    
    public static final String PROPERTY_ID = "ID";
    private long ID;
    
    public static final String PROPERTY_FIRST_NAME = "FIRST_NAME";
    private String firstName;
    
    public static final String PROPERTY_LAST_NAME = "LAST_NAME";
    private String lastName;
    
    public static final String PROPERTY_OFFICE = "OFFICE";
    private Office office;

    public Patient() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        long oldValue = this.ID;
        this.ID = ID;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_ID, oldValue, this.ID);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        String oldValue = this.firstName;
        this.firstName = firstName;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_FIRST_NAME, oldValue, this.firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        String oldValue = this.lastName;
        this.lastName = lastName;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_LAST_NAME, oldValue, this.lastName);
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        Office oldValue = this.office;
        this.office = office;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_OFFICE, oldValue, this.office);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.ID ^ (this.ID >>> 32));
        hash = 89 * hash + Objects.hashCode(this.firstName);
        hash = 89 * hash + Objects.hashCode(this.lastName);
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
        final Patient other = (Patient) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<Patient ID=\"").append(ID).append("\">");
        builder.append("<FirstName>").append(firstName != null ? firstName : "NULL")
                .append("</FirstName>");
        builder.append("<LastName>").append(lastName != null ? lastName : "NULL")
                .append("</LastName>");
        builder.append("</Patient>");
        
        return builder.toString();
    }
    
    // JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON 
    //   JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON 
    
    @Override
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
           .add(PROPERTY_ID, getID())
           .add(PROPERTY_FIRST_NAME, getFirstName())
           .add(PROPERTY_LAST_NAME, getLastName())
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
