package com.example.numbers.mapper;

import com.example.numbers.dto.UserDTO;
import com.example.numbers.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        return null;
    }

    public User toUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public List<UserDTO> toUserDTOList(List<User> users) {
        return null;
    }
}
