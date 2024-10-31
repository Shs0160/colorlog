package color_log.colorlog.dto;

public class resultDTO {
    private ImagePathDTO imagePath;
    private String result;

    private Long lightNum;

    private Long frameNum;

    public ImagePathDTO getImagePath() {
        return imagePath;
    }

    public void setImagePath(ImagePathDTO imagePath) {
        this.imagePath = imagePath;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getLightNum() {
        return lightNum;
    }

    public void setLightNum(Long lightNum) {
        this.lightNum = lightNum;
    }

    public Long getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(Long frameNum) {
        this.frameNum = frameNum;
    }
}
