package resourceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageSongDto {
    private Integer id;
    private String filePath;
    private String fileName;
    private String storageType;
    Map<String, String> userMetadata;
    private String bucketName;
}