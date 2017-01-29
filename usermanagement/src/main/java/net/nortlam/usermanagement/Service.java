package net.nortlam.usermanagement;

import net.nortlam.usermanagement.notify.NotificationError;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.ws.rs.NotFoundException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import javax.jms.Topic;
import org.bson.Document;

import net.nortlam.usermanagement.entity.Country;
import net.nortlam.usermanagement.entity.Role;
import net.nortlam.usermanagement.entity.User;
import net.nortlam.usermanagement.error.CreateException;
import net.nortlam.usermanagement.error.MissingInformationException;
import net.nortlam.usermanagement.error.NoContentException;
import net.nortlam.usermanagement.notify.Notification;
import net.nortlam.usermanagement.notify.Operation;
import net.nortlam.usermanagement.setup.MongoProvider;

@Stateless
public class Service {

    private static final Logger LOG = Logger.getLogger(Service.class.getName());
    
    @EJB
    private MongoProvider provider;
    
    @EJB
    private NotificationError error;
    
    @EJB
    private Notification notification;
    
    public void create(Role role) throws CreateException {
        try {
            // Insert a new role 
            getRoles().insertOne(role.toDocument());
            
            // Notify of operation
            notification.notify(Operation.CREATE, "role", role.toJson());
            
        } catch(MongoWriteException ex) {
            error.notify("Creating a new Role into Database", role.toJson(), "MongoWriteException", ex.getMessage());
            LOG.log(Level.WARNING, "### MONGO WRITE EXCEPTION:{0}", ex.getMessage());
            throw new CreateException(ex);
        } catch(MongoWriteConcernException ex) {
            error.notify("Creating a new Role into Database", role.toJson(), "MongoWriteConcernException", ex.getMessage());
            LOG.log(Level.WARNING, "### MONGO WRITE CONCERN EXCEPTION:{0}", ex.getMessage());
            throw new CreateException(ex);
        } catch(MongoException ex) {
            error.notify("Creating a new Role into Database", role.toJson(), "MongoException", ex.getMessage());
            LOG.log(Level.WARNING, "### MONGO EXCEPTION:{0}", ex.getMessage());
            throw new CreateException(ex);
        }
    }
    
    public Collection<Role> fetchAllRoles() throws NoContentException {
        Collection<Role> result = new ArrayList<>();
        for(Document document: getRoles().find().sort(Sorts.ascending(Role.TAG_NAME))) {
            try {
                result.add(new Role(document));
            } catch(MissingInformationException ex) {
                // It won't happen
            }
        }
        
        if(result.isEmpty()) throw new NoContentException();
        
        return result;
    }
    
    private MongoCollection<Document> getUsers() {
        return provider.getDatabase().getCollection(User.COLLECTION);
    }

    private MongoCollection<Document> getRoles() {
        return provider.getDatabase().getCollection(Role.COLLECTION);
    }
}
