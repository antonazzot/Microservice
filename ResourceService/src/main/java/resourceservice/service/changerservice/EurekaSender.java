package resourceservice.service.changerservice;

import resourceservice.model.SongDTO;
import resourceservice.model.StorageSongDto;

public interface EurekaSender {

    public SongDTO extractMetadata(SongDTO songDTO);

    public String getMetadataFromBD(Integer id);

    Integer SendToStorage(SongDTO songDTO);

    StorageSongDto getStorageSong(Integer id);

    void changeStage(Integer id);
}
