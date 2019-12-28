package net.burakkaratas.learning.struct;

import com.sun.org.apache.xpath.internal.operations.String;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriority {

  public static void main(String[] args) throws NamingException {
    InitialContext initialContext = new InitialContext();
    Queue queue = (Queue) initialContext.lookup("queue/appQueue");

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext context = cf.createContext()) {

      String[] messages = {"1", "2", "3"};
    }
  }

}
