package resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import resourceservice.model.StorageSongDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AmazonService {
    private final AmazonS3 amazonS3;

    @Async
    public byte[] getSongById(Integer id, String bucketName) {
        S3Object object = amazonS3.getObject(bucketName, id.toString());
        byte[] audiobyte = null;
        try {
            audiobyte = object.getObjectContent().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return audiobyte;
    }

    @Async
    public void deleteSongs(List<StorageSongDto> songs) {
        songs.forEach(song -> amazonS3.deleteObject(song.getBucketName(), song.getId().toString()));

    }
    @Async
    public String saveExtractSong(StorageSongDto extractSong) {
        File file = new File(extractSong.getFilePath());
        String bucketName = extractSong.getBucketName();
        amazonS3.createBucket(extractSong.getBucketName());
        return amazonS3.putObject(bucketName, extractSong.getId().toString(), file).getVersionId();
    }
}
