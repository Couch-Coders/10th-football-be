package couch.football.response.stadium;

import couch.football.domain.stadium.File;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileResponse {

    private Long id;
    private String imageUrl;

    @Builder
    public FileResponse(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    static public FileResponse mapToDto(File file) {

        return FileResponse.builder()
                .id(file.getId())
                .imageUrl(file.getImageUrl())
                .build();
    }
}
