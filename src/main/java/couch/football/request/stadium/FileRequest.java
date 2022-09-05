package couch.football.request.stadium;

import couch.football.domain.stadium.File;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileRequest {

    private String imageUrl;

    @Builder
    public FileRequest(String imageUrl) {

        this.imageUrl = imageUrl;
    }

    static public File mapToEntity(FileRequest fileRequest) {

        return File.builder()
                .imageUrl(fileRequest.getImageUrl())
                .build();

    }
}
