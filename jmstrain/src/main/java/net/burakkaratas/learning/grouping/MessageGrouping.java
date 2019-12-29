package net.burakkaratas.learning.grouping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageGrouping {

  public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

    InitialContext initialContext = new InitialContext();
    final Queue reqQueue = (Queue) initialContext.lookup("queue/reqQueue");
    try (
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext();
        JMSContext jmsContext2 = cf.createContext();) {
      final JMSProducer producer = jmsContext.createProducer();
      final ConcurrentHashMap<String, String> receivedMessages = new ConcurrentHashMap<>();

      final JMSConsumer consumer1 = jmsContext2.createConsumer(reqQueue);
      final JMSConsumer consumer2 = jmsContext2.createConsumer(reqQueue);

      consumer1.setMessageListener(
          new IamListener("consumer1", receivedMessages));
      consumer2.setMessageListener(
          new IamListener("consumer2", receivedMessages));

      int count = 10;
      TextMessage[] messages = new TextMessage[count];
      for (int i = 0; i < count; i++) {
        messages[i] = jmsContext.createTextMessage("Group-0 message " + i);
        // kapayıp açıp test edebilirsin
        messages[i].setStringProperty("JMSXGroupID", "Group-0");
        producer.send(reqQueue, messages[i]);
      }

      Thread.sleep(5000);

      for (TextMessage message : messages) {
        if (!receivedMessages.get(message.getText()).equals("consumer1")) {
          throw new RuntimeException("Group Message " + message.getText());
        }
      }

    }
  }
}

class IamListener implements MessageListener {

  private final String name;
  private final Map<String, String> receivedMessages;

  IamListener(String name, Map<String, String> receivedMessages) {
    this.name = name;
    this.receivedMessages = receivedMessages;
  }

  @Override
  public void onMessage(Message message) {
    TextMessage textMessage = (TextMessage) message;
    try {
      System.out.println("message received is " + textMessage.getText());
      System.out.println("name of the listener " + name);
      receivedMessages.put(textMessage.getText(), name);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }
}