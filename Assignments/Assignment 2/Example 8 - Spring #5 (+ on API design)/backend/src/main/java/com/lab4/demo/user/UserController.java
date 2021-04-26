package com.lab4.demo.user;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PutMapping
    public UserDTO edit(@RequestBody UserDTO userDTO) {
        return userService.editUser(userDTO);
    }
    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
