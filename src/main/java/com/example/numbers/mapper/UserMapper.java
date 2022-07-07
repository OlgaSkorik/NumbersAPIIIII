package com.example.numbers.mapper;

import com.example.numbers.dto.UserDTO;
import com.example.numbers.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<User> users);
}
