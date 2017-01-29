package net.nortlam.errorcollector.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/error"), 
        @ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="Auto-acknowledge")
})
public class QueueError implements MessageListener {

    private static final Logger LOG = Logger.getLogger(QueueError.class.getName());

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage) {
                TextMessage text = (TextMessage)message;
                LOG.log(Level.INFO, ">>> QueueError:Message:{0}", text.getText());
            }
        } catch(JMSException ex) {
            LOG.log(Level.SEVERE, "### JMS EXCEPTION:{0}", ex.getMessage());
        }
    }
}
