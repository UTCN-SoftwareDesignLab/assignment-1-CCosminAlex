package com.lab4.demo.user;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    private Users findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public UserDTO editUser(UserDTO userDTO) {
        Users updatedUser = Users.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(encoder.encode( userDTO.getPassword()) )
                .build();

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName(ERole.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
        roles.add(defaultRole);

        updatedUser.setRoles(roles);
        userRepository.delete(userRepository.findById(userDTO.getId()).get());
        return userMapper.toDto(userRepository.save(updatedUser));

    }

    public UserDTO createUser(UserDTO userDTO) {
        Users user = Users.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(encoder.encode( userDTO.getPassword()) )
                .build();

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName(ERole.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
        roles.add(defaultRole);

        user.setRoles(roles);

        return userMapper.toDto(userRepository.save(user));

    }



}
