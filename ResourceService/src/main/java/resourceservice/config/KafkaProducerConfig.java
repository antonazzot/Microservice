package resourceservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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

    @Bean
    public KafkaTemplate<String, String> kafkaStrTemplate(@Autowired ProducerFactory<String, String> producerObjectFactory) {
        return new KafkaTemplate<>(producerObjectFactory);
    }

    /**
     * Delete by id's list Producer
     */

    @Bean
    public ProducerFactory<String, List<Integer>> producerListFactory() {
        return new DefaultKafkaProducerFactory<>(producerStrConfig());
    }

    @Bean
    public KafkaTemplate<String, List<Integer>> kafkaDeleteTemplate(@Autowired ProducerFactory<String, List<Integer>> producerListFactory) {
        return new KafkaTemplate<>(producerListFactory);
    }

    /**
     * Get metadata by id's Producer
     */

    public Map<String, Object> producerStrConfig() {
        HashMap<String, Object> prop = new HashMap<>();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return prop;
    }

    @Bean
    public ProducerFactory<String, Integer> producerGetMetaFactory() {
        return new DefaultKafkaProducerFactory<>(producerStrConfig());
    }

    @Bean
    public KafkaTemplate<String, Integer> kafkaGetMetaTemplate(@Autowired ProducerFactory<String, Integer> producerGetMetaFactory) {
        return new KafkaTemplate<>(producerGetMetaFactory);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaStrListenerContainerFactory) {
        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                kafkaStrListenerContainerFactory.createContainer("receivemeta");
        repliesContainer.getContainerProperties().setGroupId("group9");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }


    /**
     * Store song and recive id
     **/

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesWithIdContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaStrListenerContainerFactory) {
        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                kafkaStrListenerContainerFactory.createContainer("storeid");
        repliesContainer.getContainerProperties().setGroupId("group999");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingIdTemplate(
            @Autowired ProducerFactory<String, String> producerGetMetaFactory,
            @Autowired ConcurrentMessageListenerContainer<String, String> repliesWithIdContainer) {
        return new ReplyingKafkaTemplate<>(producerGetMetaFactory, repliesWithIdContainer);
    }


    /**
     * Send Id and Get StorageSongDto
     **/

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesWithStorageSongDtoIdContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaStrListenerContainerFactory) {
        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                kafkaStrListenerContainerFactory.createContainer("storagesongdto");
        repliesContainer.getContainerProperties().setGroupId("group99");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingStoreDtoTemplate(
            @Autowired ProducerFactory<String, String> producerStrFactory,
            @Autowired ConcurrentMessageListenerContainer<String, String> repliesWithStorageSongDtoIdContainer) {
        return new ReplyingKafkaTemplate<>(producerStrFactory, repliesWithStorageSongDtoIdContainer);
    }


    @Bean
    public ReplyingKafkaTemplate<String, Integer, String> replyingTemplate(
            @Autowired ProducerFactory<String, Integer> producerGetMetaFactory,
            @Autowired ConcurrentMessageListenerContainer<String, String> repliesContainer) {
        return new ReplyingKafkaTemplate<>(producerGetMetaFactory, repliesContainer);
    }


}
