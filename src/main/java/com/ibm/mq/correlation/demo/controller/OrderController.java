package com.ibm.mq.correlation.demo.controller;

import com.ibm.mq.correlation.demo.model.OrderRequest;
import com.ibm.mq.jms.MQQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

@Slf4j
@RestController
@RequestMapping("/ibm/mq/")
public class OrderController {

    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest order) throws JMSException {
        log.info("### 1 ### Order Service sending order message '{}' to the queue", order.getMessage());
        log.info("Order identifier is '{}'", order.getIdentifier());
        MQQueue orderRequestQueue = new MQQueue("DEV.QUEUE.1");
        jmsTemplate.convertAndSend(orderRequestQueue, order.getMessage(), textMessage -> {
            textMessage.setJMSCorrelationID(order.getIdentifier());
            log.info("destination is '{}'", textMessage.getJMSDestination());
            return textMessage;
        });
        return new ResponseEntity(order, HttpStatus.ACCEPTED);
    }

    @Deprecated // this was just to show how to find a message by correlation Id
    @GetMapping
    public ResponseEntity<OrderRequest> findOrderByCorrelationId(@RequestParam String correlationId) throws JMSException {
        log.info("Looking for message '{}'", correlationId);
        String convertedId = bytesToHex(correlationId.getBytes());
        final String selectorExpression = String.format("JMSCorrelationID='ID:%s'", convertedId);
        final TextMessage responseMessage = (TextMessage) jmsTemplate.receiveSelected("DEV.QUEUE.1", selectorExpression);
        OrderRequest response = OrderRequest.builder()
                .message(responseMessage.getText())
                .identifier(correlationId)
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // You could use Apache Commons Codec library instead
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes();
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

}
