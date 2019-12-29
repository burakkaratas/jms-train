package net.burakkaratas.learning.pubsub;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class PayrollApp {

  public static void main(String[] args) throws NamingException, JMSException {
    InitialContext initialContext = new InitialContext();
    Topic topic = (Topic) initialContext.lookup("topic/empTopic");
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()) {
      final JMSConsumer consumer = jmsContext.createConsumer(topic);
      final Message receive = consumer.receive();
      final Employee employee = receive.getBody(Employee.class);
      System.out.println("PayrollApp :: " + employee);
    }
  }

}
