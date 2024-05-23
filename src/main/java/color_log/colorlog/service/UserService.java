package color_log.colorlog.service;

import color_log.colorlog.domain.User;
import color_log.colorlog.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import color_log.colorlog.domain.PhotoGroup;
import color_log.colorlog.repository.PhotoGroupRepository;

import java.io.IOException;

@Service
@Getter
@Setter
public class UserService {

    private final UserRepository userRepository;
    PhotoGroup photoGroup = new PhotoGroup();


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long getNextMaxUserId(){
        Long maxUserId = userRepository.findMaxUserId();
        if (maxUserId == null) {
            return 1L; // 만약 DB에 아무런 데이터가 없다면 photogroupid는 1부터 시작합니다.
        } else {
            return maxUserId + 1;
        }
    }

    @Transactional
    public Long getMaxUserId(){
        Long maxUserId = userRepository.findMaxUserId();
        if (maxUserId == null) {
            return 1L; // 만약 DB에 아무런 데이터가 없다면 photogroupid는 1부터 시작합니다.
        } else {
            return maxUserId;
        }
    }

    @Transactional
    public User getUserById(Long UserId) {
        // photoGroupId에 해당하는 PhotoGroup을 DB에서 조회하여 반환합니다.
        // 예시로 findById 메서드를 사용합니다.
        return userRepository.findById(UserId)
                .orElseGet(() -> {
                    // PhotoGroup이 없을 때 대체할 동작을 여기에 구현
                    return new User(); // 예를 들어 기본값을 반환하거나 새로운 객체를 생성하여 반환할 수 있습니다.
                });
    }

    public String getResultById(Long userId) {
        // userId에 따른 결과를 반환하는 로직 추가
        User user = getUserById(userId);
        return user != null ? user.getResult() : null;
    }

    @Transactional
    public void processUserData(String result, String resultImagePath, String facePaletteImagePath) {
        User user = new User();
        user.setResult(result);
        user.setResultImagePath(resultImagePath);
        user.setFacePaletteImagePath(facePaletteImagePath); // 새로운 필드 설정

        userRepository.save(user);
    }



}


