package color_log.colorlog.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="User_id",  nullable = false, updatable = false)
    @Schema(description = "user id", example = "21")
    private Long UserId;

    @Schema(description = "result", example = "여름쿨톤")
    private String result;

    private String resultImagePath;

    private String facePaletteImagePath;

    @ManyToOne
    @JoinColumn(name = "photo_group_id")
    private PhotoGroup photoGroup;

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long UserId) {
        UserId = UserId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultImagePath() {
        return resultImagePath;
    }

    public void setResultImagePath(String resultImagePath) {
        this.resultImagePath = resultImagePath;
    }

    public PhotoGroup getPhotoGroup() {
        return photoGroup;
    }

    public void setPhotoGroupId(PhotoGroup photoGroup) {
        this.photoGroup = photoGroup;
    }

    public String getFacePaletteImagePath() {
        return facePaletteImagePath;
    }

    public void setFacePaletteImagePath(String facePaletteImagePath) {
        this.facePaletteImagePath = facePaletteImagePath;
    }

}


