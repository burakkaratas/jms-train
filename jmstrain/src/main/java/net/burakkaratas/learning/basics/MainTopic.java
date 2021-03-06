package net.burakkaratas.learning.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MainTopic {

  public static void main(String[] args) {

    InitialContext initialContext = null;
    Connection connection = null;

    try {
      initialContext = new InitialContext();
      Topic topic = (Topic) initialContext.lookup("topic/appTopic");

      ConnectionFactory connectionFactory = (ConnectionFactory) initialContext
          .lookup("ConnectionFactory");
      connection = connectionFactory.createConnection();

      Session session = connection.createSession();
      MessageProducer producer = session.createProducer(topic);

      MessageConsumer consumer1 = session.createConsumer(topic);
      MessageConsumer consumer2 = session.createConsumer(topic);

      TextMessage tuts = session.createTextMessage("Tuts");
      producer.send(tuts);
      connection.start();

      TextMessage receive1 = (TextMessage) consumer1.receive();
      TextMessage receive2 = (TextMessage) consumer2.receive();

      System.out.println("consumer1 :: " + receive1.getText());
      System.out.println("consumer2 :: " + receive2.getText());

    } catch (NamingException e) {
      e.printStackTrace();
    } catch (JMSException e) {
      e.printStackTrace();
    } finally {
      if (null != initialContext) {
        try {
          initialContext.close();
        } catch (NamingException e) {
          e.printStackTrace();
        }
      }

      if (null != connection) {
        try {
          connection.close();
        } catch (JMSException e) {
          e.printStackTrace();
        }
      }
    }

  }

}
