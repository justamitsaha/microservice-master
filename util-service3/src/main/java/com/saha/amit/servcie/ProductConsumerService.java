package com.saha.amit.servcie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saha.amit.dto.ProductDTO;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumerService {
    private final ObjectMapper objectMapper;
    Log log = LogFactory.getLog(ProductConsumerService.class);

    public ProductConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean processRecord(ConsumerRecord<String, String> consumerRecord) {
        ProductDTO productDTO = null;
        try {
            productDTO = objectMapper.readValue(consumerRecord.value(), ProductDTO.class);
            log.info("RECEIVED PRODUCT IN MAIN CONSUMER --> " + productDTO + " RECORD " + consumerRecord);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (null != productDTO) {
            if (productDTO.getProductDescription().contains("chinese"))
                throw new IllegalArgumentException("Chinese mal fuck off");
            if (productDTO.getProductDescription().contains("pakistani"))
                throw new RecoverableDataAccessException("");
            else {
                log.info("PRODUCT PROCESSED SUCCESSFULLY --> " + productDTO + " RECORD " + consumerRecord);
                log.info("SENDING ACKNOWLEDGEMENT  " + productDTO.getProductName());
                return true;
            }
        } else
            throw new RuntimeException("Where is data ?");
    }
}
