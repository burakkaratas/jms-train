package net.burakkaratas.learning.struct;

import java.util.HashMap;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MainReqRep {

  public static void main(String[] args) throws NamingException, JMSException {

    InitialContext initialContext = new InitialContext();
    Queue reqQueue = (Queue) initialContext.lookup("queue/reqQueue");
//    Queue repQueue = (Queue) initialContext.lookup("queue/repQueue");

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext context = cf.createContext()) {
      final JMSProducer producer = context.createProducer();
      final TextMessage firstMessage = context.createTextMessage("hi");
      //dynamic create
      final TemporaryQueue repQueue = context.createTemporaryQueue();
      firstMessage.setJMSReplyTo(repQueue);
      producer.send(reqQueue, firstMessage);
      // send etmezsen eğer message id falan gelmiyor haberin ola..
      System.out.println(firstMessage.getJMSMessageID());
      final HashMap<String, TextMessage> reqHashMap = new HashMap<>();
      reqHashMap.put(firstMessage.getJMSMessageID(), firstMessage);

      final TextMessage receiveMessage = (TextMessage) context.createConsumer(reqQueue).receive();
      System.out.println(receiveMessage.getText());

      final JMSProducer replyProducer = context.createProducer();
      final TextMessage replyMessage = context.createTextMessage("i am busy");
      replyMessage.setJMSCorrelationID(receiveMessage.getJMSMessageID());
      replyProducer.send(receiveMessage.getJMSReplyTo(), replyMessage);

      final TextMessage lastMessage = (TextMessage) context.createConsumer(repQueue).receive();
      System.out.println(lastMessage.getText());
      System.out.println(lastMessage.getJMSCorrelationID());

      final TextMessage textMessage = reqHashMap.get(lastMessage.getJMSCorrelationID());
      System.out.println(textMessage.getText());


    }
  }

}
