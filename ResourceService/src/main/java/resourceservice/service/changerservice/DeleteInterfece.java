package resourceservice.service.changerservice;

import resourceservice.model.StorageSongDto;

import java.util.List;

public interface DeleteInterfece {
    void deleteFromMetadata(Integer[] deleteId);

    List<Integer> deleteEverewhere(List<StorageSongDto> songDtoList);
}
