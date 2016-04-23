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
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import net.nortlam.sickplace.common.JSON;
import net.nortlam.sickplace.common.PropertySupport;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
public class Subscription extends PropertySupport implements Serializable, JSON{
    
    public static final String PROPERTY_ID = "ID";
    private long ID;
    
    public static final String PROPERTY_START = "start";
    private Date start;
    
    public static final String PROPERTY_END = "end";
    private Date end;
    
    private PropertyChangeSupport pss;

    public Subscription() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        long oldValue = this.ID;
        this.ID = ID;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_ID, oldValue, this.ID);
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        Date oldValue = this.start;
        this.start = start;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_START, oldValue, this.start);
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        Date oldValue = this.end;
        this.end = end;
        getPropertyChangeSupport().firePropertyChange(PROPERTY_END, oldValue, this.end);
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
