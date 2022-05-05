package resourceservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.exception.MyCustomAppException;
import resourceservice.model.StorageSongDto;
import resourceservice.service.changerservice.ChangerSender;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {

    private final AmazonService amazonService;
    private final FileValidator fileValidatorService;
    private final ChangerSender changerSender;

    public Integer saveSong(MultipartFile file) {
        log.info("File to save:={} ", file.getOriginalFilename()+" " +file.getSize() );

        if (fileValidatorService.validateFile(file))
            throw new MyCustomAppException("Fail doesn't passed validate, File mustn't be empty or file format not supported");
        return changerSender.sendToStageService(file);
    }

    public byte[] getSongById(Integer id) {
        StorageSongDto extractSong = changerSender.getExtractSong(id);
        if (!(extractSong == null) && extractSong.getStorageType().equalsIgnoreCase("PERMANENT"))
            return amazonService.getSongById(id, extractSong.getBucketName());
        else
            throw new MyCustomAppException("Song with id " + id + " does not exist or it's in stage state");
    }

    public List<Integer> deleteSongById(Integer[] id) {
        return changerSender.deleteFromAllStorage(id);
    }

    public String getMetaById(Integer id) {
        return changerSender.getMetaById(id);
    }

    public void uploadSong(Integer id) {
        StorageSongDto extractSong = changerSender.getExtractSong(id);
        amazonService.saveExtractSong(extractSong);
        changerSender.changeStorageStage(id);
    }
}
