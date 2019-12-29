package net.burakkaratas.learning.guarenteed;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ProducerApp {

  public static void main(String[] args) throws NamingException, JMSException {
    InitialContext initialContext = new InitialContext();
    final Queue reqQueue = (Queue) initialContext.lookup("queue/reqQueue");
//    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//        JMSContext jmsContext = cf.createContext(JMSContext.CLIENT_ACKNOWLEDGE)) {
//      final JMSProducer producer = jmsContext.createProducer();
//      producer.send(reqQueue, "Message1");
//    }

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED)) {
      final JMSProducer producer = jmsContext.createProducer();
      producer.send(reqQueue, "Message1");
//      jmsContext.commit();
      producer.send(reqQueue, "Message2");
      jmsContext.commit();
//      jmsContext.rollback();
    }
  }
}
