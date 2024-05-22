package color_log.colorlog.controller;

import color_log.colorlog.S3Uploader;
import color_log.colorlog.domain.PhotoGroup;
import color_log.colorlog.domain.User;
import color_log.colorlog.dto.ImagePathDTO;
import color_log.colorlog.dto.PhotoGroupDTO;
import color_log.colorlog.dto.resultDTO;
import color_log.colorlog.repository.UserRepository;
import color_log.colorlog.service.PhotoGroupService;
import color_log.colorlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
//@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final PhotoGroupService photoGroupService;
    private final UserRepository userRepository;
    private S3Uploader s3Uploader;

    @Autowired
    public UserController(UserService userService, S3Uploader s3Uploader, PhotoGroupService photoGroupService, UserRepository userRepository){
        this.photoGroupService = photoGroupService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.s3Uploader = s3Uploader;
    }

    @PostMapping(value = "/user_upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> uploadUserData(

        @RequestParam("result") String result,
        @RequestParam("resultImage") MultipartFile resultImage

    ) {
        try {
            Long UserId = userService.getNextMaxUserId();
            String resultImagePath = s3Uploader.uploadFileToS3(resultImage, UserId + "_resultImage");

            userService.processUserData(result, resultImagePath);
            return ResponseEntity.ok().body("User uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            PhotoGroupDTO.ResponseDTO responseDTO = new PhotoGroupDTO.ResponseDTO();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload user");
        }
    }

    @GetMapping(value = "/get_result",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getResult(@RequestParam Long userId) {
        try {
            User user = userService.getUserById(userId);
            PhotoGroup photoGroup = photoGroupService.getPhotoGroupById(userId);

            if (user == null || photoGroup == null) {
                throw new Exception("No data found for userId: " + userId);
            }

            ImagePathDTO imagePath = new ImagePathDTO();
            imagePath.setResultImagePath(user.getResultImagePath());

            String result = userService.getResultById(userId); // userService에서 결과를 가져오는 메서드 호출

            resultDTO resultDTO = new resultDTO();
            resultDTO.setImagePath(imagePath);
            resultDTO.setResult(result);

            return ResponseEntity.ok(resultDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get result.");
        }
    }
    @GetMapping("/qr-code")
    @ResponseBody
    public ResponseEntity<Object> generateQRCodeLink() {
        try {
            // 현재 데이터베이스에서 가장 큰 userId 가져오기
            Long maxUserId = userRepository.findMaxUserId();

            // QR 코드 생성 링크 생성
            String qrCodeLink = "http://192.168.0.75:8080/user/" + maxUserId;

            // 생성된 QR 코드 링크를 포함한 응답 반환
            Map<String, String> response = new HashMap<>();
            response.put("link", qrCodeLink);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate QR code link.");
        }
    }

}
