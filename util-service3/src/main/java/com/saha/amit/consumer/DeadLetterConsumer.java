package com.saha.amit.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saha.amit.dto.ProductDTO;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterConsumer implements AcknowledgingMessageListener<String, String> {

    private final ObjectMapper objectMapper;
    Log log = LogFactory.getLog(DeadLetterConsumer.class);

    public DeadLetterConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    //@KafkaListener(topics = "${topics.dlt}", groupId = "product-RETRY-group")
    public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {

        ProductDTO productDTO = null;
        try {
            productDTO = objectMapper.readValue(consumerRecord.value(), ProductDTO.class);
            log.info("DEAD RECEIVED --> " + productDTO + " RECORD " + consumerRecord);
            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
