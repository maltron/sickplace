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
import java.util.Collection;
import net.nortlam.sickplace.common.PropertySupport;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
public class Office extends PropertySupport implements Serializable {
    
    public static final String PROPERTY_ID = "ID";
    private long ID;
    
    public static final String PROPERTY_PATIENTS = "patients";
    private Collection<Patient> patients;
    
    private PropertyChangeSupport pss;

    public Office() {
    }
    
    public void setID(long ID) {
        long oldValue = this.ID;
        this.ID = ID;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_ID, oldValue, this.ID);
    }
    
    public long getID() {
        return ID;
    }

    public Collection<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Collection<Patient> patients) {
        Collection<Patient> oldValue = this.patients;
        this.patients = patients;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_PATIENTS, oldValue, this.patients);
    }
    
    // PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER 
    //  PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER PROPERTY CHANGE LISTENER 

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        if(pss == null) pss = new PropertyChangeSupport(this);
        
        return pss;
    }
}
