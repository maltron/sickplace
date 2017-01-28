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
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.NotFoundException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import javax.jms.Topic;
import org.bson.Document;

import net.nortlam.example.entity.Country;
import net.nortlam.example.entity.Role;
import net.nortlam.example.entity.User;
import net.nortlam.example.error.CreateException;
import net.nortlam.example.error.NoContentException;
import net.nortlam.example.setup.MongoProvider;

@Stateless
public class Service {

    private static final Logger LOG = Logger.getLogger(Service.class.getName());
    
    public static final String JMS_FACTORY = "java:/AMQConnectionFactory";
    @Resource(mappedName=JMS_FACTORY)
    private ConnectionFactory connectionFactory;

    public static final String TOPIC_INFORM = "java:/topic/inform";
    @Resource(mappedName=TOPIC_INFORM)
    private Topic topicInform;    

    @EJB
    private MongoProvider provider;
    
    public void create(Role role) throws CreateException {
        try {
            getRoles().insertOne(role.toDocument());
        } catch(MongoWriteException ex) {
            LOG.log(Level.WARNING, "### MONGO WRITE EXCEPTION:{0}", ex.getMessage());
            throw new CreateException(ex);
        } catch(MongoWriteConcernException ex) {
            LOG.log(Level.WARNING, "### MONGO WRITE CONCERN EXCEPTION:{0}", ex.getMessage());
            throw new CreateException(ex);
        } catch(MongoException ex) {
            LOG.log(Level.WARNING, "### MONGO EXCEPTION:{0}", ex.getMessage());
            throw new CreateException(ex);
        }
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

    public void sendAsync(String jndi, String message) {
        LOG.log(Level.INFO, ">>> Destination: {0}  Message: {1}",
                new Object[] {jndi, message});
        
        try(Connection connection = getConnection(); 
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            Context context = new InitialContext();
            Destination destination = (Destination)context.lookup(jndi);
            
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
            
        } catch(JMSException ex) {
            LOG.log(Level.SEVERE, "### JMS EXCEPTION:{0}", ex.getMessage());
        } catch(NamingException ex) {
            LOG.log(Level.SEVERE, "### NAMING EXCEPTION:{0}", ex.getMessage());
        } 
    }
    
    private Connection getConnection() throws JMSException {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory)context.lookup(JMS_FACTORY);
            connection = factory.createConnection(); connection.start();
            
        } catch(NamingException ex) {
            LOG.log(Level.SEVERE, "### NAMING EXCEPTION:{0}", ex.getMessage());
        }
        
        return connection;
    }
}
