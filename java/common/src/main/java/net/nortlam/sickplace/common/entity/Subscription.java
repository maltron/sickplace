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
import java.util.Date;
import net.nortlam.sickplace.common.PropertySupport;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
public class Subscription implements Serializable, PropertySupport{
    
    public static final String PROPERTY_ID = "ID";
    private long ID;
    
    public static final String PROPERTY_START = "start";
    private Date start;
    
    public static final String PROPERTY_END = "end";
    private Date end;
    
    private PropertyChangeSupport pss;

    public Subscription() {
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        Date oldValue = this.start;
        this.start = start;
        pss.firePropertyChange(PROPERTY_START, oldValue, this.start);
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        Date oldValue = this.end;
        this.end = end;
        pss.firePropertyChange(PROPERTY_END, oldValue, this.end);
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
