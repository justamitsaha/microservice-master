package com.saha.amit.consumer;

import com.saha.amit.servcie.ProductConsumerService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ProductKafkaConsumer implements AcknowledgingMessageListener<String, String> {
    private final ProductConsumerService productConsumerService;
    Log log = LogFactory.getLog(ProductKafkaConsumer.class);

    @Autowired
    public ProductKafkaConsumer(ProductConsumerService productConsumerService) {
        this.productConsumerService = productConsumerService;
    }

//    @KafkaListener(topics = {"product"})
//    public void onMessage(ConsumerRecord<String,String> consumerRecord){
//        log.info("Received  --> "+consumerRecord );
//    }

    @Override
    @KafkaListener(topics = {"${topic.main}"}, groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        log.info("CONSUMED RECORD IN ProductKafkaConsumer");
        if (productConsumerService.processRecord(consumerRecord))
            acknowledgment.acknowledge();
    }
}
