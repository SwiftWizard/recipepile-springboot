package com.recipepile.mappers;

import com.recipepile.domain.Authority;
import com.recipepile.domain.User;
import com.recipepile.domain.dtos.UserSlimDTO;
import com.recipepile.domain.dtos.UserSlimInfoDTO;
import com.recipepile.domain.dtos.post.UserPost;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;

@Mapper(componentModel="spring")
public interface UserMapper {

    Authority map(GrantedAuthority value);

    UserSlimDTO userToUserSlimDTO(User user);

    List<UserSlimDTO> userToUserSlimDTO(List<User> humanUsers);

    UserSlimInfoDTO userToUserSlimInfoDTO(User user);
    User userPostToUser(UserPost userPost);
}
