package resourceservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import resourceservice.service.SongService;
import resourceservice.service.changerservice.KafkaService;


@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaListener {
    private final SongService songService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "notification",
            groupId = "mygroup10")
    public void listener(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer id = objectMapper.readValue(message, Integer.class);

        songService.uploadSong(id);
    }


}

