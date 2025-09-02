package com.alchemist.yoru.controller;
import com.alchemist.yoru.dto.MenuItemDto;
import com.alchemist.yoru.entity.MenuItem;
import com.alchemist.yoru.service.impl.MenuItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu-item")
public class MenuItemController {

    private final MenuItemServiceImpl menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemDto> createMenuItem(@RequestBody MenuItemDto menuItemDto){
        MenuItemDto newMenuItem = menuItemService.saveMenuItem(menuItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMenuItem);
    }

    @PostMapping("/save/menu-items")
    public ResponseEntity<List<MenuItemDto>> saveMenuItems(@RequestBody List<MenuItemDto> menuItemDtoList){
        List<MenuItemDto> menuItems = menuItemService.saveMenuItemList(menuItemDtoList);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItems);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDto>> getAllMenuItems() {
        List<MenuItemDto> menuItems = menuItemService.getMenuItemList();
        return ResponseEntity.ok().body(menuItems);
    }

    @GetMapping("{id}")
    public ResponseEntity<MenuItemDto> getMenuItem(@PathVariable("id") long id) {
        MenuItemDto menuItemDto = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok().body(menuItemDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MenuItem> deleteMenuItem(@PathVariable("id") long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/permanentDelete/{id}")
    public ResponseEntity<MenuItem> permanentDeleteMenuItem(@PathVariable("id") long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<MenuItemDto> uploadImage(@PathVariable long id, @RequestParam("image") MultipartFile file) throws IOException {
        MenuItemDto menuItem = menuItemService.updateImage(id, file);
        return ResponseEntity.ok().body(menuItem);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable long id) {

        byte[] imageBytes = menuItemService.getImage(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<MenuItemDto>> getMenuItemByName(@PathVariable String name) {
        List<MenuItemDto> menuItems = menuItemService.getMenuItemByName(name);
        return ResponseEntity.ok().body(menuItems);
    }

    @PutMapping("/{id}/delete-image")
    public ResponseEntity deleteImage(@PathVariable long id) {
        menuItemService.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}