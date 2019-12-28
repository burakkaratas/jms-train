package net.burakkaratas.learning.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MainQueue {

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
      TextMessage textMessage = session.createTextMessage("Selam");
      producer.send(textMessage);

      MessageConsumer consumer = session.createConsumer(queue);
      connection.start();
      TextMessage receive = (TextMessage) consumer.receive();
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
