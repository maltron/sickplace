package net.nortlam.example;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
@Stateless
public class NotificationError implements Serializable {

    private static final Logger LOG = Logger.getLogger(NotificationError.class.getName());
    
    public static final String JMS_FACTORY = "java:/AMQConnectionFactory";
    @javax.annotation.Resource(lookup=JMS_FACTORY)
    private ConnectionFactory connectionFactory;

    public static final String QUEUE_ERROR = "java:/queue/error";
    @javax.annotation.Resource(lookup=QUEUE_ERROR)
    private Queue queueError;
    
    public NotificationError() {
        LOG.log(Level.INFO, ">>> NotificationError()");
    }
    
    public void notify(String description, String data, String exception, String exceptionMessage) {
        try (Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            
            // Componse a message into a JSON format
            JsonObjectBuilder builder = Json.createObjectBuilder();
            if(description != null) builder.add("description", description);
            if(data != null) builder.add("data", data);
            if(exception != null) builder.add("exception", exception);
            if(exceptionMessage != null) builder.add("exceptionMessage", exceptionMessage);
            
            MessageProducer producer = session.createProducer(queueError);
            producer.send(session.createTextMessage(builder.build().toString()));
            
        } catch(JMSException ex) {
            LOG.log(Level.SEVERE, "### JMS EXCEPTION:{0}", ex.getMessage());
        }
    }

}
