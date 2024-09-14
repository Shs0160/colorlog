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
            return 1L;
        } else {
            return maxUserId + 1;
        }
    }

    @Transactional
    public Long getMaxUserId(){
        Long maxUserId = userRepository.findMaxUserId();
        if (maxUserId == null) {
            return 1L;
        } else {
            return maxUserId;
        }
    }

    @Transactional
    public User getUserById(Long UserId) {
        return userRepository.findById(UserId)
                .orElseGet(() -> {
                    return new User();
                });
    }

    public String getResultById(Long userId) {
        User user = getUserById(userId);
        return user != null ? user.getResult() : null;
    }

    @Transactional
    public void processUserData(String result, String resultImagePath, String facePaletteImagePath) {
        User user = new User();
        user.setResult(result);
        user.setResultImagePath(resultImagePath);
        user.setFacePaletteImagePath(facePaletteImagePath);

        userRepository.save(user);
    }



}


