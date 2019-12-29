package net.burakkaratas.learning.filter;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ClaimMain {

  public static void main(String[] args) throws NamingException, JMSException {
    InitialContext initialContext = new InitialContext();
    Queue queue = (Queue) initialContext.lookup("queue/claimQueue");
    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()) {
      final JMSProducer producer = jmsContext.createProducer();
//      final JMSConsumer consumer = jmsContext.createConsumer(queue, "hospitalId=1");
//      final JMSConsumer consumer = jmsContext.createConsumer(queue, "amount between 1 and 2000");
//      final JMSConsumer consumer = jmsContext.createConsumer(queue, "name like '%i_em'");
//      final JMSConsumer consumer = jmsContext.createConsumer(queue, "type IN ('lova', 'avo')");
      final JMSConsumer consumer = jmsContext
          .createConsumer(queue, "type IN ('lova', 'avo') OR JMSPriority BETWEEN 3 AND 6");

      final ObjectMessage objectMessage = jmsContext.createObjectMessage();
//      objectMessage.setIntProperty("hospitalId", 1);
//      objectMessage.setDoubleProperty("amount", 10000);
//      objectMessage.setStringProperty("name", "Gizem");
      objectMessage.setStringProperty("type", "lova");
      Claim claim = new Claim();
      claim.setHospitalId(1);
      claim.setDoctorName("Gizem");
      claim.setDoctorType("lova");
      claim.setClaimAmount(123);
      objectMessage.setObject(claim);
      producer.send(queue, objectMessage);

      final Claim claim1 = consumer.receiveBody(Claim.class);
      System.out.println(claim1);
    }
  }
}
