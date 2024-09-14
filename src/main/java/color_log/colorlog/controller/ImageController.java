package color_log.colorlog.controller;

import color_log.colorlog.domain.PhotoGroup;
import color_log.colorlog.dto.PhotoGroupDTO;
import color_log.colorlog.service.*;
import color_log.colorlog.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import color_log.colorlog.S3Uploader;

import java.io.IOException;


@Controller
//@RequiredArgsConstructor
@RequestMapping("api/photogroup")
public class ImageController {

    private final PhotoGroupService photoGroupService;
    private final UserService userService;
    private S3Uploader s3Uploader;



    @Autowired
    public ImageController(PhotoGroupService photoGroupService, S3Uploader s3Uploader, UserService userService) {
        this.photoGroupService = photoGroupService;
        this.userService = userService;
        this.s3Uploader = s3Uploader;
    }

    @PostMapping(value = "/photogroup_upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> uploadPhotoGroup (


            @RequestPart("image") MultipartFile image1,
            @RequestPart("video") MultipartFile video
    ) {

        try {
            Long photoGroupId = photoGroupService.getNextPhotoGroupId();

            String imagePath = s3Uploader.uploadFileToS3(image1, photoGroupId + "_image");
            String videoPath = s3Uploader.uploadFileToS3(video, photoGroupId + "_video");

            photoGroupService.processPhotoGroupData(imagePath, videoPath);
            return ResponseEntity.ok().body("Photo group uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            PhotoGroupDTO.ResponseDTO responseDTO = new PhotoGroupDTO.ResponseDTO();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload photo group.");
        }
    }

    @GetMapping(value = "/get_photogroup", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> downloadPhotoGroup(@RequestParam Long userId) {
        try {
            Long maxPhotoGroupId = photoGroupService.getMaxPhotoGroupId();

            Long maxUserId = userService.getMaxUserId();

            if (!maxPhotoGroupId.equals(maxUserId)) {
                throw new Exception("photogroupid and userid mismatch");
            }

                    PhotoGroup photoGroup = photoGroupService.getPhotoGroupById(userId);

            PhotoGroupPathDTO photoGroupPaths = new PhotoGroupPathDTO();
            photoGroupPaths.setImagePath(photoGroup.getImagePath());
            photoGroupPaths.setVideoPath(photoGroup.getVideoPath());

            return ResponseEntity.ok().body(photoGroupPaths);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download photo group.");
        }
    }



}
