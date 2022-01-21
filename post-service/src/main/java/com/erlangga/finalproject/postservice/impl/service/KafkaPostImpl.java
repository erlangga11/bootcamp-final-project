package com.erlangga.finalproject.postservice.impl.service;

import com.erlangga.finalproject.postservice.api.service.KafkaPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPostImpl implements KafkaPost {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String topic = "logService";

    @Override
    public void sendLog(String message) {
        kafkaTemplate.send(topic, message);
    }
}
