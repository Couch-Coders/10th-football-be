package couch.football.controller.stadium;

import couch.football.service.stadium.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("files")
public class FileController {

    private final FileService fileService;

    // 파일 입력 및 저장
    @PostMapping("")
    public List<String> uploadFile(@RequestParam("files") List<MultipartFile> files) throws IOException {

        return fileService.uploadFile(files);
    }

    // 파일 보내기
    @GetMapping("/{fileId}")
    public byte[] getFile(@PathVariable String fileId) {

        return fileService.getFile(fileId);
    }
}
