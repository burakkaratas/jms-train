package net.burakkaratas.learning.struct;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MainMessageDelay {

  public static void main(String[] args)
      throws NamingException, InterruptedException, JMSException {

    InitialContext initialContext = new InitialContext();
    Queue appQueue = (Queue) initialContext.lookup("queue/appQueue");

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext context = cf.createContext()) {
      final JMSProducer producer = context.createProducer();
      producer.setDeliveryDelay(3000);
      producer.send(appQueue, "delay");

      final Message message = context.createConsumer(appQueue).receive(5000);
      System.out.println("app " + message);

    }

  }

}
