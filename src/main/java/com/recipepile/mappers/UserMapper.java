package com.recipepile.mappers;

import com.recipepile.domain.User;
import com.recipepile.domain.dtos.UserSlimDTO;
import com.recipepile.domain.dtos.UserSlimInfoDTO;
import com.recipepile.domain.dtos.post.UserPost;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel="spring")
public interface UserMapper {

    UserSlimDTO userToUserSlimDTO(User user);

    List<UserSlimDTO> userToUserSlimDTO(List<User> humanUsers);

    UserSlimInfoDTO userToUserSlimInfoDTO(User user);
    User userPostToUser(UserPost userPost);
}
