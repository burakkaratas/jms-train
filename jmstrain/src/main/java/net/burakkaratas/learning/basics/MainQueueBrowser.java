package net.burakkaratas.learning.basics;

import java.util.Enumeration;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MainQueueBrowser {

  public static void main(String[] args) {

    InitialContext initialContext = null;
    Connection connection = null;
    try {
      initialContext = new InitialContext();

      ConnectionFactory connectionFactory = (ConnectionFactory) initialContext
          .lookup("ConnectionFactory");
      Queue queue = (Queue) initialContext.lookup("queue/appQueue");

      connection = connectionFactory.createConnection();
      Session session = connection.createSession();

      MessageProducer producer = session.createProducer(queue);
      TextMessage textMessage1 = session.createTextMessage("Selam");
      TextMessage textMessage2 = session.createTextMessage("Tuts");

      producer.send(textMessage1);
      producer.send(textMessage2);

      QueueBrowser browser = session.createBrowser(queue);
      Enumeration messagesEnumeration = browser.getEnumeration();
      while (messagesEnumeration.hasMoreElements()){
        TextMessage textMessage = (TextMessage) messagesEnumeration.nextElement();
        System.out.println(textMessage.getText());
      }

      MessageConsumer consumer = session.createConsumer(queue);
      connection.start();
      TextMessage receive = (TextMessage) consumer.receive();
      System.out.println(receive.getText());
      receive = (TextMessage) consumer.receive();
      System.out.println(receive.getText());

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

      if (null != connection){
        try {
          connection.close();
        } catch (JMSException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
