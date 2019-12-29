package net.burakkaratas.learning.p2p;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class EligibilityCheckerListener implements MessageListener {

  @Override
  public void onMessage(Message message) {
    ObjectMessage objectMessage = (ObjectMessage) message;
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()) {
      InitialContext initialContext = new InitialContext();
      final Queue repQueue = (Queue) initialContext.lookup("queue/repQueue");
      Patient patient = (Patient) objectMessage.getObject();
      System.out.println(patient);

      final MapMessage mapMessage = jmsContext.createMapMessage();

      if (patient.getInsuranceProvide().equals("abc")) {
        mapMessage.setBoolean("checked", true);
      } else {
        mapMessage.setBoolean("checked", false);
      }

      final JMSProducer producer = jmsContext.createProducer();
      producer.send(repQueue, mapMessage);
    } catch (JMSException | NamingException e) {
      e.printStackTrace();
    }
  }

}
