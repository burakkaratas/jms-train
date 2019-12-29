package net.burakkaratas.learning.pubsub;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class WelnessApp {

  public static void main(String[] args) throws NamingException, JMSException {
    InitialContext initialContext = new InitialContext();
    Topic topic = (Topic) initialContext.lookup("topic/empTopic");
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()) {

      final JMSConsumer consumer1 = jmsContext.createSharedConsumer(topic, "shared");
      final JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "shared");

      for (int i = 0; i < 10; i++) {
        final Message receive = consumer1.receive();
        final Employee employee = receive.getBody(Employee.class);
        System.out.println("WelnessApp1 :: " + employee);

        final Message receive2 = consumer2.receive();
        final Employee employee2 = receive2.getBody(Employee.class);
        System.out.println("WelnessApp2 :: " + employee2);
      }
    }
  }

}
