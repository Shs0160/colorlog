package color_log.colorlog.controller;

import color_log.colorlog.domain.PhotoGroup;
import color_log.colorlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import color_log.colorlog.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;



import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    /*@PostMapping("/processUserData")
    @ResponseBody
    public String processUserData(@RequestParam("result") String result) {
        try {
            userService.processUserData(result);
            return "User data processed successfully.";
        } catch (IOException e) {
            return "Error processing user data: " + e.getMessage();
        }
    }*/

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> userPage(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
       /* ObjectMapper objectMapper = new ObjectMapper();
        try {
            String userJson = objectMapper.writeValueAsString(user);
            return ResponseEntity.ok(userJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }*/
    }


}