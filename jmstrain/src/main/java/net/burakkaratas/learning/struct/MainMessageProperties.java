package net.burakkaratas.learning.struct;

import java.util.UUID;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MainMessageProperties {

  public static void main(String[] args)
      throws NamingException, InterruptedException, JMSException {

    InitialContext initialContext = new InitialContext();
    Queue appQueue = (Queue) initialContext.lookup("queue/appQueue");

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext context = cf.createContext()) {
      final JMSProducer producer = context.createProducer();
      TextMessage textMessage = context.createTextMessage("properties");
      textMessage.setBooleanProperty("isActive", true);
      textMessage.setStringProperty("txid", UUID.randomUUID().toString());
      producer.send(appQueue, textMessage);

      final TextMessage message = (TextMessage) context.createConsumer(appQueue).receive(5000);
      final boolean aBoolean = message.getBooleanProperty("isActive");
      final String txid = message.getStringProperty("txid");
      System.out.println("app " + message.getText() + " " + aBoolean + " " + txid);

    }

  }

}
