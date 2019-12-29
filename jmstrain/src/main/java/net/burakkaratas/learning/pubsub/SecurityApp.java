package net.burakkaratas.learning.pubsub;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SecurityApp {

  public static void main(String[] args)
      throws NamingException, JMSException, InterruptedException {
    InitialContext initialContext = new InitialContext();
    Topic topic = (Topic) initialContext.lookup("topic/empTopic");
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()) {
      jmsContext.setClientID("securityApp");
      JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subs1");
      consumer.close();

      Thread.sleep(10000);

      consumer = jmsContext.createDurableConsumer(topic, "subs1");
      final Message receive = consumer.receive();
      final Employee employee = receive.getBody(Employee.class);
      System.out.println("Security :: " + employee);
      consumer.close();
      jmsContext.unsubscribe("subs1");
    }
  }

}
