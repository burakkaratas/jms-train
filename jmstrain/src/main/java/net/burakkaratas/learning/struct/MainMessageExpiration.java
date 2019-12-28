package net.burakkaratas.learning.basics;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MainJmsContext {

  public static void main(String[] args) throws NamingException {

    InitialContext initialContext = new InitialContext();
    Queue queue = (Queue) initialContext.lookup("queue/appQueue");

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext context = cf.createContext()) {
      context.createProducer().send(queue, "Selam Sana");
      final String message = context.createConsumer(queue).receiveBody(String.class);
      System.out.println(message);
    }

  }

}
