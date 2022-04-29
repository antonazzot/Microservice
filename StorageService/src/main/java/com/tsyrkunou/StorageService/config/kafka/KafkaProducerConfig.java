package com.tsyrkunou.StorageService.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapservers;

    public Map<String, Object> producerConfig() {
        HashMap<String, Object> prop = new HashMap<>();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return prop;
    }

    @Bean
    public ProducerFactory<String, String> producerObjectFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }


    /**
     * replying
     */

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesDtoContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaJsonListenerContainerFactory) {
        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                kafkaJsonListenerContainerFactory.createContainer("uploadmeta");
        repliesContainer.getContainerProperties().setGroupId("mygroup118");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }


}
