package couch.football.service.stadium;

import com.google.cloud.storage.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final Bucket bucket;

    // 파일 입력 및 저장
    public List<String> uploadFile(List<MultipartFile> files) throws IOException {

        // File 저장위치를 선언
        String blob = "files/" ;

        // 파일을 Bucket에 저장
        List<String> urls = new ArrayList<>();

        for(MultipartFile file : files) {
            String url = blob + UUID.randomUUID().toString();
            bucket.create(url, file.getBytes());
            urls.add("/" + url);
        }
        return urls;
    }

    // 파일 보내기
    public byte[] getFile(String imageUrl) {

        return bucket.get("files/" + imageUrl).getContent();
    }
}
