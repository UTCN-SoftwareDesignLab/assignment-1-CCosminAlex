package com.lab4.demo.user.mapper;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.Users;import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "users.username")
    })
    UserMinimalDTO userMinimalFromUser(Users users);

    @Mappings({
            @Mapping(target = "name", source = "users.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(Users users);

    @AfterMapping
    default void populateRoles(Users users, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(users.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

    UserDTO toDto(Users users);

    Users fromDto(UserDTO userDTO);
}
