package net.nortlam.example;

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
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import org.bson.Document;

import net.nortlam.example.entity.Country;
import net.nortlam.example.entity.Role;
import net.nortlam.example.entity.User;
import net.nortlam.example.error.CreateException;
import net.nortlam.example.error.MissingInformationException;
import net.nortlam.example.error.NoContentException;
import net.nortlam.example.setup.MongoProvider;

@Stateless
public class Service {

    private static final Logger LOG = Logger.getLogger(Service.class.getName());
    
    public static final String JMS_FACTORY = "java:/AMQConnectionFactory";
    @Resource(lookup=JMS_FACTORY)
    private ConnectionFactory connectionFactory;

    public static final String TOPIC_INFORM = "java:/topic/inform";
    @Resource(lookup=TOPIC_INFORM)
    private Topic topicInform;    
    
    @EJB
    private MongoProvider provider;
    
    @EJB
    private NotificationError error;
    
    public void create(Role role) throws CreateException {
        try {
            // Insert a new role 
            getRoles().insertOne(role.toDocument());
            
            // Notify of operation
            notify("CREATE", "role", role.toString());
            
        } catch(MongoWriteException ex) {
            error.notify("Creating a new Role into Database", role.toString(), "MongoWriteException", ex.getMessage());
            LOG.log(Level.WARNING, "### MONGO WRITE EXCEPTION:{0}", ex.getMessage());
            throw new CreateException(ex);
        } catch(MongoWriteConcernException ex) {
            error.notify("Creating a new Role into Database", role.toString(), "MongoWriteConcernException", ex.getMessage());
            LOG.log(Level.WARNING, "### MONGO WRITE CONCERN EXCEPTION:{0}", ex.getMessage());
            throw new CreateException(ex);
        } catch(MongoException ex) {
            error.notify("Creating a new Role into Database", role.toString(), "MongoException", ex.getMessage());
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
    
    public Collection<Country> fetchAllCountries(Collection<String> fields) throws NoContentException {
        boolean hasProjection = !fields.isEmpty();
        Document projection = new Document();
        if(hasProjection) for(String field: fields) projection.append(field, 1);
        
        // List of documents sorted by Name
        Collection<Country> result = new ArrayList<>();
        for(Document document: hasProjection ? getCountries().find()
                 .projection(projection).sort(Sorts.ascending(Country.TAG_NAME)) :
                getCountries().find().sort(Sorts.ascending(Country.TAG_NAME)))
            result.add(new Country(document));
        
        if(result.isEmpty()) throw new NoContentException();
        
        return result;
    }
    
    public Country fetchInternet(String internet) throws NotFoundException {
        Document document = getCountries().find(
                Filters.eq(Country.TAG_INTERNET, internet)).first();
        if(document == null) throw new NotFoundException();
        
        return new Country(document);
    }

    private MongoCollection<Document> getCountries() {
        return provider.getDatabase().getCollection(Country.COLLECTION);
    }
    
    private MongoCollection<Document> getUsers() {
        return provider.getDatabase().getCollection(User.COLLECTION);
    }

    private MongoCollection<Document> getRoles() {
        return provider.getDatabase().getCollection(Role.COLLECTION);
    }
    
    public void notify(String operation, String entity, String content) {
        try (Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            
            // Componse a message into a JSON format
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("operation", operation);
            builder.add("entity", entity);
            builder.add("content", content);
            
            MessageProducer producer = session.createProducer(topicInform);
            producer.send(session.createTextMessage(builder.build().toString()));
            
        } catch(JMSException ex) {
            LOG.log(Level.SEVERE, "### JMS EXCEPTION:{0}", ex.getMessage());
        }
    }
}
