package net.burakkaratas.learning.p2p;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class EligibilityCheckerApp {

  public static void main(String[] args) throws NamingException {
    InitialContext initialContext = new InitialContext();
    final Queue reqQueue = (Queue) initialContext.lookup("queue/reqQueue");
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()) {
      final JMSConsumer consumer = jmsContext.createConsumer(reqQueue);
      consumer.setMessageListener(new EligibilityCheckerListener());

      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
