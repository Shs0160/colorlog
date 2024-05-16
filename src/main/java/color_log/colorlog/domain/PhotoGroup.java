package color_log.colorlog.domain;


import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "photo_group")
public class PhotoGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_group_id", nullable = false, updatable = false)
    private Long photoGroupId;

    private String imagePath;

    private String videoPath;

    public Long getPhotoGroupId() {
        return photoGroupId;
    }

    public void setPhotoGroupId(Long photoGroupId) {
        this.photoGroupId = photoGroupId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
