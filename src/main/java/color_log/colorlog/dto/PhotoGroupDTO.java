package color_log.colorlog.dto;

import org.springframework.web.multipart.MultipartFile;

public class PhotoGroupDTO {

    private MultipartFile resultImageFile;
    private MultipartFile imageFile;

    private MultipartFile videoFile;

    public static class ResponseDTO {
        private String message;
        private Object data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    public MultipartFile getResultImageFile() {
        return resultImageFile;
    }

    public void setResultImageFile(MultipartFile resultImageFile) {
        this.resultImageFile = resultImageFile;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public MultipartFile getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(MultipartFile videoFile) {
        this.videoFile = videoFile;
    }



}
