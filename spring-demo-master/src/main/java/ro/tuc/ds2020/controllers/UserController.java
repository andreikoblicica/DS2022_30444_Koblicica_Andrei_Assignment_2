package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;

import static ro.tuc.ds2020.UrlMapping.*;


@RestController
@RequestMapping(USERS)
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<UserDTO>> allUsers() {
        List<UserDTO> users= userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(USER_ID)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO user) {
        UserDTO userDTO= userService.update(user);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO user) {
        UserDTO userDTO=  userService.save(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
