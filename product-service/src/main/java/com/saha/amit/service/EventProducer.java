package com.saha.amit.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saha.amit.dto.ProductDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class EventProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final StreamBridge streamBridge;

    private Log log = LogFactory.getLog(KafkaTemplate.class);

    @Autowired
    public EventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, StreamBridge streamBridge) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.streamBridge = streamBridge;
    }

    public void sendRabbitMqCommunication(ProductDto productDto) {
        log.info("Sending Communication request for the details: {} " + productDto);
        var result = streamBridge.send("sendCommunication-out-0", productDto);
        log.info("Is the Communication request successfully triggered ? : {} " + result);
    }

    public CompletableFuture<SendResult<String, String>> sendKafkaEvent(ProductDto productDto) throws JsonProcessingException {

        String key = productDto.getCategory();
        String value = objectMapper.writeValueAsString(productDto);

        var completableFuture = kafkaTemplate.send("product", key, value);
        return completableFuture.whenComplete((stringStringSendResult, throwable) ->
        {
            if (null != throwable)
                log.error("Error in sending to kafka");
            else
                log.info("Message successfully send to kafka" + stringStringSendResult);
        });
    }

    public SendResult<String, String> sendKafkaEvent2(ProductDto productDto) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String key = productDto.getCategory();
        String value = objectMapper.writeValueAsString(productDto);
        //Blocking call
        return kafkaTemplate.send("product", key, value).get(3, TimeUnit.SECONDS);
    }

//    public CompletableFuture<SendResult<String, String>> sendKafkaEvent3(ProductDto productDto) throws JsonProcessingException {
//
//        String key = productDto.getCategory();
//        String value = objectMapper.writeValueAsString(productDto);
//
//        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);
//
//        var completableFuture = kafkaTemplate.send("product", key, value);
//        return completableFuture.whenComplete((stringStringSendResult, throwable) ->
//        {
//            if (null != throwable)
//                log.error("Error in sending to kafka");
//            else
//                log.info("Message successfully send to kafka" + stringStringSendResult);
//        });
//    }
//
//    private ProducerRecord<Integer, String> buildProducerRecord(String key, String value, String topic) {
//
//
//        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
//
//        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
//    }

}
