package com.ibm.mq.correlation.demo.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Slf4j
@Component
public class OrderResponseListener {

    //earlier it was DEV.QUEUE.1 when corId to corId
    @JmsListener(destination = "DEV.QUEUE.2")
    public void receive(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        log.info("### 4 ### Order Service received message response : {} with correlation id: {}",
                textMessage.getText(), textMessage.getJMSCorrelationID());
        // do some business logic here, like updating the order in the database
    }

}
