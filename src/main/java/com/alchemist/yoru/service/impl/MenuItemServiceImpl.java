package com.alchemist.yoru.service.impl;

import com.alchemist.yoru.Tenant.Context;
import com.alchemist.yoru.dto.MenuItemDto;
import com.alchemist.yoru.entity.MenuItem;
import com.alchemist.yoru.entity.ScratchCard;
import com.alchemist.yoru.exceptions.ResourceNotFoundException;
import com.alchemist.yoru.repo.MenuItemRepository;
import com.alchemist.yoru.service.IMenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuItemServiceImpl implements IMenuItemService {

    private final MenuItemRepository menuItemRepository;

    private static final String Menu_Item_String = "Menu Item";

    @Override
    public MenuItemDto saveMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = dtoToEntity(menuItemDto);
        menuItem.setTenantId(Context.getTenantId());
        return entityToDto(menuItemRepository.save(menuItem));
    }

    @Override
    public List<MenuItemDto> saveMenuItemList(List<MenuItemDto> menuItemDtos){
        List<MenuItemDto> menuItems = new ArrayList<>();
        for(MenuItemDto menuItemDto : menuItemDtos){
            menuItems.add(saveMenuItem(menuItemDto));
        }
        return menuItems;
    }

    @Override
    public List<MenuItemDto> getMenuItemList() {
        String id = Context.getTenantId();
        return entityListToDTOList(menuItemRepository.findByTenantId(id));
    }

    @Override
    public MenuItemDto getMenuItemById(long id) {
        String tenantId = Context.getTenantId();
        MenuItem menuItem = menuItemRepository.findByIdAndTenantId(id,tenantId).orElseThrow( ()
                -> new ResourceNotFoundException( "Menu item with Id"+ id +"Not Found") );
        return entityToDto(menuItem);
    }

    @Override
    public List<MenuItemDto> getMenuItemByName(String name) {
        String tenantId = Context.getTenantId();
        return entityListToDTOList(menuItemRepository.findByNameContainsAndTenantId(name,tenantId));
    }

    @Override
    public void deleteMenuItem(long id) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(id);
        if (menuItem.isPresent()) {
            List<ScratchCard> scratchCards = menuItem.get().getScratchCards();
            for (int i = 0; i < scratchCards.size(); i++) {
                scratchCards.get(i).setMenuItem(null);
            }
            menuItem.get().setActive(false);
            menuItemRepository.save(menuItem.get());
        } else {
            throw new ResourceNotFoundException("menu item"+id);
        }
    }

    @Override
    public String permanentDeleteMenuItem(long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow( ()
                -> new ResourceNotFoundException("Unable to delete Menu item with Id"+ id ) );
        menuItemRepository.delete(menuItem);
        return "Menu Item deleted successfully";
    }

    @Override
    public byte[] getImage(long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image of Menu item with Id"+ id +"Not Found"));
        return menuItem.getImage();
    }

    @Override
    public MenuItemDto updateImage(long id, MultipartFile file) throws IOException {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MenuItem not found with id " + id));

        // Convert the MultipartFile to a byte array
        byte[] bytes = file.getBytes();

        // Set the image field of the MenuItem object
        menuItem.setImage(bytes);

        // Save the MenuItem object to the database

        return entityToDto(menuItemRepository.save(menuItem));
    }

    @Override
    public void deleteImage(long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MenuItem not found with id " + id));

        // Set the image field of the MenuItem object to null
        menuItem.setImage(null);

        // Save the MenuItem object to the database
        menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItemDto entityToDto(MenuItem menuItem) {
        MenuItemDto menuItemDto = new MenuItemDto();
        BeanUtils.copyProperties(menuItem, menuItemDto);
        return menuItemDto;
    }

    @Override
    public MenuItem dtoToEntity(MenuItemDto menuItemDto) {
        MenuItem menuItem = new MenuItem();
        BeanUtils.copyProperties(menuItemDto, menuItem);
        return menuItem;
    }

    @Override
    public List<MenuItemDto> entityListToDTOList(List<MenuItem> menuItems){
        return menuItems.
                stream().map(this::entityToDto).
                collect(Collectors.toList());
    }

}