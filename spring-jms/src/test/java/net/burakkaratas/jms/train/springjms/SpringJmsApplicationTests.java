package net.burakkaratas.jms.train.springjms;

import net.burakkaratas.jms.train.springjms.sender.MessageSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringJmsApplicationTests {

  @Autowired
  MessageSender messageSender;

  @Test
  void testSendAndReceive() {
    messageSender.send("Hello");
  }

}
