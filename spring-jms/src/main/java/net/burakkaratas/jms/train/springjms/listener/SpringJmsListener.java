package net.burakkaratas.jms.train.springjms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SpringJmsListener {

  @JmsListener(destination = "${spring.jms.myQueue}")
  public void receive(String message) {
    System.out.println("received :: " + message);
  }

}
