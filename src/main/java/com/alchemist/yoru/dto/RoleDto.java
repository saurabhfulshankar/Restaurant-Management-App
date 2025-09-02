package com.alchemist.yoru.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {

    private List<PermissionDto> permissions;
}
