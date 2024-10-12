package color_log.colorlog.service;

import color_log.colorlog.domain.User;
import color_log.colorlog.domain.PhotoGroup;
import color_log.colorlog.repository.PhotoGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PhotoGroupService {

    private final PhotoGroupRepository photoGroupRepository;

    public PhotoGroupService(PhotoGroupRepository photoGroupRepository) {
        this.photoGroupRepository = photoGroupRepository;
    }

    @Transactional
    public Long getNextPhotoGroupId() {

        Long maxPhotoGroupId = photoGroupRepository.findMaxPhotoGroupId();
        if (maxPhotoGroupId == null) {
            return 1L;
        } else {
            return maxPhotoGroupId + 1;
        }
    }

    @Transactional
    public Long getMaxPhotoGroupId(){
        Long maxPhotoGroupId = photoGroupRepository.findMaxPhotoGroupId();
        if (maxPhotoGroupId == null) {
            return 1L;
        } else {
            return maxPhotoGroupId;
        }
    }

    @Transactional
    public PhotoGroup getPhotoGroupById(Long photoGroupId) {
        return photoGroupRepository.findById(photoGroupId)
                .orElseGet(() -> {
                    return new PhotoGroup();
                });
    }

    @Transactional
    public void processPhotoGroupData(String imagePath, String videoPath) throws IOException {
        /*String imagePath1 = saveFileToStorage(image1);
        String imagePath2 = saveFileToStorage(image2);
        String imagePath3 = saveFileToStorage(image3);
        String imagePath4 = saveFileToStorage(image4);
        String videoPath = saveFileToStorage(video);*/

        PhotoGroup photoGroup = new PhotoGroup();
        photoGroup.setImagePath(imagePath);
        photoGroup.setVideoPath(videoPath);

        photoGroupRepository.save(photoGroup);
    }

    /*
    private String saveFileToStorage(MultipartFile file) throws IOException {
        return "/path/to/storage/" + file.getOriginalFilename();
    }*/


}
