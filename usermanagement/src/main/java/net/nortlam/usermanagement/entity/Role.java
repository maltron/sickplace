package net.nortlam.usermanagement.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonValue;
import net.nortlam.usermanagement.error.MissingInformationException;
import org.bson.Document;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
public class Role implements Serializable {

    public static final String COLLECTION = "roles";

    private static final Logger LOG = Logger.getLogger(Role.class.getName());
    
    public static final String TAG_NAME = "name";
    private String name;
    
    public Role() {
    }
    
    public Role(String name) {
        this.name = name;
    }
    
    public Role(Document document) throws MissingInformationException {
        this.name = document.getString(TAG_NAME);
        if(this.name == null) throw new MissingInformationException("tag <name> is missing");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
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
        final Role other = (Role) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("{\"%s\":\"%s\"}", TAG_NAME, this.name);
    }
    
    public JsonValue toJson() {
        return Json.createObjectBuilder().add(TAG_NAME, this.name).build();
    }
    
    public Document toDocument() {
        return new Document(TAG_NAME, this.name);
    }
}
