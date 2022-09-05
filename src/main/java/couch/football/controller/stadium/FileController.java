package couch.football.controller.stadium;

import couch.football.request.stadium.FileRequest;
import couch.football.response.stadium.FileResponse;
import couch.football.service.stadium.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;

    // 파일 입력 및 저장
    @PostMapping("/files")
    public ResponseEntity<FileResponse> uploadFile(@Valid @RequestBody FileRequest fileRequest,
                                                   @RequestParam MultipartFile imageUrl) throws IOException {

        FileResponse file = fileService.uploadFile(fileRequest, imageUrl.getBytes());

        return new ResponseEntity<>(file, HttpStatus.CREATED);
    }

    // 파일 보내기
    @GetMapping("/files/{fileId}")
    public byte[] downloadProfile(@PathVariable("fileId") Long fileId) {

        return fileService.getProfile(fileId);
    }
}
