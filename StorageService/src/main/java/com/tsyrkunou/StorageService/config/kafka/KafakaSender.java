package com.tsyrkunou.StorageService.config.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafakaSender {

    private final ReplyingKafkaTemplate<String, String, String> replyingDtoTemplate;
    private final KafkaTemplate<String, String> kafkaStrTemplate;

    public void sendMessageWithoutCallback(String topic, String objectString) throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, objectString);
        ListenableFuture<SendResult<String, String>> future = kafkaStrTemplate.send(record);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("**********" + "failed" + "*************");
                log.error("Error in replying ={}", ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("**********" + "success" + "*************");
            }
        });

        future.get(10000, TimeUnit.MILLISECONDS);

    }

    public String sendCommonMessageWithStringCallback(String topic, String objectString) throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {


        ProducerRecord<String, String> record = new ProducerRecord<>(topic, objectString);
        RequestReplyFuture<String, String, String> future = replyingDtoTemplate.sendAndReceive(record);

        future.addCallback(new ListenableFutureCallback<ConsumerRecord<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("**********" + "failed" + "*************");
                log.error("Error in replying ={}", ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }

            @Override
            public void onSuccess(ConsumerRecord<String, String> result) {
                System.out.println("**********" + "success" + "*************");
            }
        });

        return future.get(10000, TimeUnit.MILLISECONDS).value();
    }

}

