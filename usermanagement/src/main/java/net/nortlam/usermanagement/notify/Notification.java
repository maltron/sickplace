package net.nortlam.usermanagement.notify;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
@Stateless
public class Notification {

    private static final Logger LOG = Logger.getLogger(Notification.class.getName());

    @Resource(lookup="java:/AMQConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup="java:/topic/inform")
    private Topic topicInform;    
    
    public void notify(Operation operation, String entity, JsonValue content) {
        try (Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            
            // Componse a message into a JSON format
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("operation", operation.toString());
            builder.add("entity", entity);
            builder.add("content", content);
            
            MessageProducer producer = session.createProducer(topicInform);
            producer.send(session.createTextMessage(builder.build().toString()));
            
        } catch(JMSException ex) {
            LOG.log(Level.SEVERE, "### JMS EXCEPTION:{0}", ex.getMessage());
        }
    }
    
}
