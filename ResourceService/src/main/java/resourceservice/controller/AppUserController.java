package resourceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import resourceservice.model.auth.AppUser;
import resourceservice.service.authservice.AuthService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AppUserController {
    private final AuthService authService;

    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<List<AppUser>> getUser() {
       return ResponseEntity.of(Optional.ofNullable(authService.getAllUser()));
    }
}
