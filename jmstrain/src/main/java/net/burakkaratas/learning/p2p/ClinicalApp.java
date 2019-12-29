package net.burakkaratas.learning.p2p;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ClinicalApp {

  public static void main(String[] args) throws NamingException, JMSException {
    InitialContext initialContext = new InitialContext();
    final Queue reqQueue = (Queue) initialContext.lookup("queue/reqQueue");
    final Queue repQueue = (Queue) initialContext.lookup("queue/repQueue");
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()) {
      final JMSProducer producer = jmsContext.createProducer();
      ObjectMessage objectMessage = jmsContext.createObjectMessage();
      Patient patient = new Patient(1, "asbc", 30d, 200d);
      objectMessage.setObject(patient);
      for (int i = 0; i < 10; i++) {
        producer.send(reqQueue, objectMessage);
      }
      final JMSConsumer consumer = jmsContext.createConsumer(repQueue);
      final MapMessage receive = (MapMessage) consumer.receive(3000);
      System.out.println("checked :: " + receive.getBoolean("checked"));
    }
  }

}
