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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import net.nortlam.sickplace.common.PropertySupport;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
public class Pacient implements Serializable, PropertySupport {
    
    private PropertyChangeSupport pss;
    
    public static final String PROPERTY_ID = "ID";
    private long ID;
    
    public static final String PROPERTY_FIRST_NAME = "FIRST_NAME";
    private String firstName;
    
    public static final String PROPERTY_LAST_NAME = "LAST_NAME";
    private String lastName;

    public Pacient() {
        pss = new PropertyChangeSupport(this);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        long oldValue = this.ID;
        this.ID = ID;
        pss.firePropertyChange(PROPERTY_ID, oldValue, this.ID);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        String oldValue = this.firstName;
        this.firstName = firstName;
        pss.firePropertyChange(PROPERTY_FIRST_NAME, oldValue, this.firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        String oldValue = this.lastName;
        this.lastName = lastName;
        pss.firePropertyChange(PROPERTY_LAST_NAME, oldValue, this.lastName);
    }
    
    // PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER 
    //  PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER 
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pss.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pss.removePropertyChangeListener(listener);
    }
}
