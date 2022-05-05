package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import resourceservice.model.StorageSongDto;
import resourceservice.service.AmazonService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeleteService implements DeleteInterfece {

    private final RestTemplate restTemplate;
    private final AmazonService amazonService;
    private final String DELETE_PARAM = "deleteid";

    @Override
    public void deleteFromMetadata(Integer[] deleteId) {
        Map<String, Integer> deleteMap = new HashMap<>();
        for (Integer id : deleteId) {
            deleteMap.put(DELETE_PARAM, id);
        }
        restTemplate.delete("http://METADATA/metadata/delete/" + "{" + DELETE_PARAM + "}", deleteMap);
    }

    @Override
    public List<Integer> deleteEverewhere(List<StorageSongDto> songDtoList) {
        Map<String, Integer> deleteMap = new HashMap<>();

        for (StorageSongDto storageSongDto : songDtoList) {
            deleteMap.put(DELETE_PARAM, storageSongDto.getId());
        }
        amazonService.deleteSongs(songDtoList);
        restTemplate.delete("http://METADATA/metadata/delete/" + "{" + DELETE_PARAM + "}", deleteMap);
        restTemplate.delete("http://STORAGE/store/delete/" + "{" + DELETE_PARAM + "}", deleteMap);

        return deleteMap.values().stream().toList();
    }
}
