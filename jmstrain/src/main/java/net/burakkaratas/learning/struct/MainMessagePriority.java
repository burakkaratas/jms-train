package net.burakkaratas.learning.struct;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MainMessagePriority {

  public static void main(String[] args) throws NamingException {
    InitialContext initialContext = new InitialContext();
    Queue queue = (Queue) initialContext.lookup("queue/appQueue");

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext context = cf.createContext()) {
      final JMSProducer producer = context.createProducer();
      String[] messages = {"one", "two", "tree"};

      //default priority is 4
      producer.setPriority(2);
      producer.send(queue, messages[0]);

      producer.setPriority(9);
      producer.send(queue, messages[1]);

      producer.setPriority(5);
      producer.send(queue, messages[2]);

      final JMSConsumer consumer = context.createConsumer(queue);
      for (int i = 0; i < 3; i++) {
        System.out.println(consumer.receiveBody(String.class));
      }

    }
  }

}
