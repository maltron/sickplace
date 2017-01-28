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
import javax.json.Json;
import javax.json.JsonObject;
import net.nortlam.sickplace.common.JSON;
import net.nortlam.sickplace.common.PropertySupport;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
public class Plan extends PropertySupport implements Serializable, JSON {
    
    public static final String PROPERTY_ID = "ID";
    private long ID;
    
    private PropertyChangeSupport pss;
    
    public Plan() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        long oldValue = this.ID;
        this.ID = ID;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_ID, oldValue, this.ID);
    }
    
    // JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON 
    //   JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON JSON 
    
    @Override
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
           .add(PROPERTY_ID, getID())
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
