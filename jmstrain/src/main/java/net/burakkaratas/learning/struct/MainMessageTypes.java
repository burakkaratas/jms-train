package net.burakkaratas.learning.struct;

import java.io.Serializable;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MainMessageTypes {

  public static void main(String[] args)
      throws NamingException, JMSException {

    InitialContext initialContext = new InitialContext();
    Queue appQueue = (Queue) initialContext.lookup("queue/appQueue");

    try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext context = cf.createContext()) {
      final JMSProducer producer = context.createProducer();
//      final BytesMessage bytesMessage = context.createBytesMessage();
//      bytesMessage.writeUTF("tutkus");
//      bytesMessage.writeInt(328);
//      producer.send(appQueue, bytesMessage);
//
//      final BytesMessage message = (BytesMessage) context.createConsumer(appQueue).receive(5000);
//      System.out.println("app " + message.readUTF() + " " + message.readInt());

//      final StreamMessage streamMessage = context.createStreamMessage();
//      streamMessage.writeBoolean(true);
//      streamMessage.writeByte((byte) 12);
//      producer.send(appQueue, streamMessage);
//
//      final StreamMessage message = (StreamMessage) context.createConsumer(appQueue).receive(5000);
//      System.out.println("app " + message.readBoolean() + " " + message.readByte());

//      final MapMessage mapMessage = context.createMapMessage();
//      mapMessage.setBoolean("tut", true);
//      mapMessage.setBoolean("bus", false);
//      producer.send(appQueue, mapMessage);
//
//      final MapMessage message = (MapMessage) context.createConsumer(appQueue).receive(5000);
//      System.out.println("app " + message.getBoolean("tut") + " " + message.getBoolean("bus"));

      final ObjectMessage objectMessage = context.createObjectMessage();
      final Patient patient = new Patient();
      patient.setId(1);
      patient.setName("burak");
      objectMessage.setObject(patient);
      producer.send(appQueue, objectMessage);

//      final ObjectMessage message = (ObjectMessage) context.createConsumer(appQueue).receive(5000);
//      System.out.println("app " + message.getObject());

      final Patient receivePatient = context.createConsumer(appQueue).receiveBody(Patient.class);
      System.out.println("app " + receivePatient);

    }
  }

  static class Patient implements Serializable {

    private int id;
    private String name;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Patient{" +
          "id=" + id +
          ", name='" + name + '\'' +
          '}';
    }
  }
}
