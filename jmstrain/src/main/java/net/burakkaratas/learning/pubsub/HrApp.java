package net.burakkaratas.learning.pubsub;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class HrApp {

  public static void main(String[] args) throws NamingException {
    InitialContext initialContext = new InitialContext();
    Topic topic = (Topic) initialContext.lookup("topic/empTopic");
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()) {
      Employee employee = new Employee(1, "burak", "developer");
      for (int i = 0; i < 10; i++) {
        jmsContext.createProducer().send(topic, employee);
      }
      System.out.println("HrApp " + employee);
    }

  }

}
