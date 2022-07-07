package com.example.numbers.service;

import com.example.numbers.dao.RoleRepository;
import com.example.numbers.dao.UserRepository;
import com.example.numbers.entity.Role;
import com.example.numbers.entity.Status;
import com.example.numbers.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void registration(User user) {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setTypeOfRole("USER");
        roles.add(role);
        user.setRoleList(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(roles);
        user.setStatus(Status.ACTIVE);
        role.setUser(user);
        User regUser = userRepository.save(user);
        roleRepository.save(role);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        userRepository.deleteUserByUsername(username);
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
