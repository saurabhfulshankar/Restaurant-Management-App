package com.alchemist.yoru.service;


import com.alchemist.yoru.dto.UserDto;
import com.alchemist.yoru.dto.response.RegisterResponse;
import com.alchemist.yoru.entity.User;

import java.util.List;

public interface IUserService {
    RegisterResponse register(UserDto userData);

}
