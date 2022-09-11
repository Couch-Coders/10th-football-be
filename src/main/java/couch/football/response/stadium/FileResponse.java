package couch.football.response.stadium;

import couch.football.domain.stadium.File;
import lombok.Data;

@Data
public class FileResponse {

    private Long id;
    private String imageUrl;

    public FileResponse(File file) {
        this.id = file.getId();
        this.imageUrl = file.getImageUrl();
    }
}
