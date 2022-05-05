package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import resourceservice.model.StorageSongDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteByKafka implements DeleteInterfece {

    private final KafkaTemplate<String, List<Integer>> kafkaListTemplate;

    @Override
    public void deleteFromMetadata(Integer[] deleteId) {
        List<Integer> integerList = Arrays.asList(deleteId);
        kafkaListTemplate.send("deletesong", integerList);
    }

    @Override
    public List<Integer> deleteEverewhere(List<StorageSongDto> songDtoList) {
        List<Integer> integerList = new ArrayList<>();
        songDtoList.forEach(song -> integerList.add(song.getId()));
        kafkaListTemplate.send("deletesong", integerList);
        return integerList;
    }
}
