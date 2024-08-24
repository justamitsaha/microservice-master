package com.saha.amit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saha.amit.dto.ProductDTO;
import com.saha.amit.producer.EventProducer;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("product")
public class EventController {

    private final EventProducer eventProducer;

    Log log = LogFactory.getLog(EventController.class);

    @Autowired
    public EventController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody ProductDTO productDto, @RequestHeader("userId") String userId) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        productDto.setUserId(Integer.parseInt(userId));
        log.info("Inside ProductController save " + productDto.toString());
        eventProducer.sendKafkaEvent(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
}
