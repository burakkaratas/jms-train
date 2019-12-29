package net.burakkaratas.learning.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.burakkaratas.learning.p2p.EligibilityCheckerListener;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class EligibilityCheckerApp {

  public static void main(String[] args) throws NamingException {
    InitialContext initialContext = new InitialContext();
    final Queue reqQueue = (Queue) initialContext.lookup("queue/reqQueue2");
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext("user2","user2")) {
      final JMSConsumer consumer = jmsContext.createConsumer(reqQueue);
      consumer.setMessageListener(new EligibilityCheckerListener());

      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
