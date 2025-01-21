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

    @PostMapping(value = "/user_upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> uploadUserData(
            @RequestParam("result") String result,
            @RequestParam("resultImage") MultipartFile resultImage,
            @RequestParam("facePalette") MultipartFile facePalette,
            @RequestParam("lightNum") Long lightNum,
            @RequestParam("frameNum") Long frameNum

    ) {
        try {
            Long userId = userService.getNextMaxUserId();

            // resultImage를 S3에 업로드
            String resultImagePath = s3Uploader.uploadFileToS3(resultImage, userId + "_resultImage");

            // facePalette를 S3에 업로드
            String facePaletteImagePath = s3Uploader.uploadFileToS3(facePalette, userId + "_facePaletteImage");

            // 결과와 경로를 데이터베이스에 저장
            userService.processUserData(result, resultImagePath, facePaletteImagePath, lightNum, frameNum);

            return ResponseEntity.ok().body("User uploaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload user");
        }
    }

    @GetMapping(value = "/get_result", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getResult(@RequestParam Long userId) {
        try {
            // userI와 매칭되는 사용자 데이터를 가져옴
            User user = userService.getUserById(userId);
            if (user == null) {
                throw new Exception("No user data found for userId: " + userId);
            }

            // userId와 매칭되는 사진 그룹 데이터를 가져옴
            PhotoGroup photoGroup = photoGroupService.getPhotoGroupById(userId);

            // 결과 데이터를 DTO로 변환
            ImagePathDTO imagePath = new ImagePathDTO();
            imagePath.setResultImagePath(user.getResultImagePath());
            imagePath.setFacePaletteImagePath(user.getFacePaletteImagePath());

            String result = user.getResult();
            Long lightNum = user.getLightNum();
            Long frameNum = user.getFrameNum();

            resultDTO resultDTO = new resultDTO();
            resultDTO.setImagePath(imagePath);
            resultDTO.setResult(result);
            resultDTO.setLightNum(lightNum);
            resultDTO.setFrameNum(frameNum);

            return ResponseEntity.ok(resultDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get result for userId: " + userId);
        }
    }

    @GetMapping("/qr-code")
    @ResponseBody
    public ResponseEntity<Object> generateQRCodeLink() {
        try {

            Long maxUserId = userRepository.findMaxUserId();

            String qrCodeLink = "https://colorlog.site/user/" + maxUserId;

            Map<String, String> response = new HashMap<>();
            response.put("link", qrCodeLink);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate QR code link.");
        }
    }

}
