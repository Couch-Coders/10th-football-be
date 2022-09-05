package couch.football.service.stadium;

import com.google.cloud.storage.Bucket;
import couch.football.domain.stadium.File;
import couch.football.repository.stadium.FileRepository;
import couch.football.request.stadium.FileRequest;
import couch.football.response.stadium.FileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final Bucket bucket;

    public FileResponse uploadFile(FileRequest fileRequest, byte[] imageUrl) {

        // File 저장위치를 선언
        String blob = "/stadiumImage/" + fileRequest.getImageUrl();

        // 이미 존재하면 파일 삭제
        if(bucket.get(blob) != null) {
            bucket.get(blob).delete();
        }
        // 파일을 Bucket에 저장
        bucket.create(blob, imageUrl);

        // DB에 파일정보 업데이트
        File file = FileRequest.mapToEntity(fileRequest);

        file.updateFile("/stadiumImage/" + file);

        File savedFile = fileRepository.save(file);

        return FileResponse.mapToDto(savedFile);
    }

    public byte[] getProfile(Long fileId) {

        return bucket.get("/files/" + fileId + "/profile").getContent();
    }
}
