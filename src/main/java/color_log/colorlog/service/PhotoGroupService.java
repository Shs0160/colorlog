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
@Transactional(readOnly = true) // 기본은 false
public class PhotoGroupService {

    private final PhotoGroupRepository photoGroupRepository;

    public PhotoGroupService(PhotoGroupRepository photoGroupRepository) {
        this.photoGroupRepository = photoGroupRepository;
    }

    @Transactional
    public Long getNextPhotoGroupId() {
        // DB에서 현재 가장 큰 photogroupid 값을 가져와서 +1 한 값을 반환합니다.
        Long maxPhotoGroupId = photoGroupRepository.findMaxPhotoGroupId();
        if (maxPhotoGroupId == null) {
            return 1L; // 만약 DB에 아무런 데이터가 없다면 photogroupid는 1부터 시작합니다.
        } else {
            return maxPhotoGroupId + 1;
        }
    }

    @Transactional
    public Long getMaxPhotoGroupId(){
        Long maxPhotoGroupId = photoGroupRepository.findMaxPhotoGroupId();
        if (maxPhotoGroupId == null) {
            return 1L; // 만약 DB에 아무런 데이터가 없다면 photogroupid는 1부터 시작합니다.
        } else {
            return maxPhotoGroupId;
        }
    }

    @Transactional
    public PhotoGroup getPhotoGroupById(Long photoGroupId) {
        // photoGroupId에 해당하는 PhotoGroup을 DB에서 조회하여 반환합니다.
        // 예시로 findById 메서드를 사용합니다.
        return photoGroupRepository.findById(photoGroupId)
                .orElseGet(() -> {
                    // PhotoGroup이 없을 때 대체할 동작을 여기에 구현
                    return new PhotoGroup(); // 예를 들어 기본값을 반환하거나 새로운 객체를 생성하여 반환할 수 있습니다.
                });
    }

    @Transactional
    public void processPhotoGroupData(String imagePath, String videoPath) throws IOException {
        // 이미지와 비디오 파일 저장
        /*String imagePath1 = saveFileToStorage(image1);
        String imagePath2 = saveFileToStorage(image2);
        String imagePath3 = saveFileToStorage(image3);
        String imagePath4 = saveFileToStorage(image4);
        String videoPath = saveFileToStorage(video);*/

        // 사진 그룹 엔티티 생성
        PhotoGroup photoGroup = new PhotoGroup();
        photoGroup.setImagePath(imagePath);
        photoGroup.setVideoPath(videoPath);

        // 사진 그룹 저장
        photoGroupRepository.save(photoGroup);
    }

    private String saveFileToStorage(MultipartFile file) throws IOException {
        // 파일을 저장하고 저장된 경로를 반환하는 로직
        // 여기서는 단순히 경로를 문자열로 반환하는 가정하에 작성하였습니다.
        return "/path/to/storage/" + file.getOriginalFilename();
    }


}
