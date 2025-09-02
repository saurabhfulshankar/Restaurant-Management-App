package com.alchemist.yoru.service;

import com.alchemist.yoru.dto.MenuItemDto;
import com.alchemist.yoru.entity.MenuItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMenuItemService {

    MenuItemDto saveMenuItem(MenuItemDto menuItemDto);

    List<MenuItemDto> saveMenuItemList(List<MenuItemDto> menuItemDtos);

    List<MenuItemDto> getMenuItemList();

    MenuItemDto getMenuItemById(long id);

    List<MenuItemDto> getMenuItemByName(String name);

    void deleteMenuItem(long id);

    String permanentDeleteMenuItem(long id);

    byte[] getImage(long id);

    MenuItemDto updateImage(long id, MultipartFile file) throws IOException;

    void deleteImage(long id);

    MenuItemDto entityToDto(MenuItem menuItem);

    MenuItem dtoToEntity(MenuItemDto menuItemDto);

    List<MenuItemDto> entityListToDTOList(List<MenuItem> menuItems);
}
