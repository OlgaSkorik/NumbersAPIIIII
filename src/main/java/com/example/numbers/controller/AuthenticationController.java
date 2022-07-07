package com.example.numbers.controller;

import com.example.numbers.configuration.jwt.JWTTokenProvider;
import com.example.numbers.dto.AuthRequestDTO;
import com.example.numbers.dto.UserDTO;
import com.example.numbers.entity.User;
import com.example.numbers.mapper.UserMapper;
import com.example.numbers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/logIn")
    public ResponseEntity<Map<Object, Object>> logIn(@RequestBody AuthRequestDTO requestDto){

        String username = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        if (userService.findByUsername(username).isPresent()) {
            User user = userService.findByUsername(username).get();

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.generateToken(username, user.getRoleList());

            Map<Object, Object> resp = new HashMap<>();
            resp.put("username", username);
            resp.put("token", token);

            return new ResponseEntity<>(resp, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/reg")
    public ResponseEntity<UserDTO> registration(@RequestBody UserDTO userDTO){
        if (userService.existByUsername(userDTO.getUsername())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.registration(userMapper.toUser(userDTO));
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<Object, Object>> logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<Object, Object> resp = new HashMap<>();

        if (auth != null) {
            resp.put("username", auth.getName());
            resp.put("session, lastAccessedTime", session.getLastAccessedTime());
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
