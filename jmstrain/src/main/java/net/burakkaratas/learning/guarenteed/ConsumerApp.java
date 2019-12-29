package net.burakkaratas.learning.guarenteed;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ConsumerApp {

  public static void main(String[] args) throws NamingException, JMSException {
    InitialContext initialContext = new InitialContext();
    final Queue reqQueue = (Queue) initialContext.lookup("queue/reqQueue");

//    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//        JMSContext jmsContext = cf.createContext(JMSContext.CLIENT_ACKNOWLEDGE)) {
//      final JMSConsumer consumer = jmsContext.createConsumer(reqQueue);
//      final TextMessage receive = (TextMessage) consumer.receive();
//      receive.acknowledge();
//      System.out.println(receive.getText());
//    }

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED)) {
      final JMSConsumer consumer = jmsContext.createConsumer(reqQueue);
      final TextMessage receive = (TextMessage) consumer.receive();
      System.out.println(receive.getText());
      jmsContext.commit();
    }
  }

}
