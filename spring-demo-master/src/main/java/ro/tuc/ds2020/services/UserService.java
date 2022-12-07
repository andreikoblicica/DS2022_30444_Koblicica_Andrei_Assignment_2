package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public UserDTO save(UserDTO user){
        return UserBuilder.toUserDTO(userRepository.save(UserBuilder.toEntity(user)));
    }

    public List<UserDTO> findAll(){
        return userRepository.findAll().stream().filter(user -> user.getRole().equals("Regular User")).map(UserBuilder::toUserDTO).collect(Collectors.toList());
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public UserDTO findById(Long id){
        Optional<User> user=userRepository.findById(id);
        return user.map(UserBuilder::toUserDTO).orElse(null);

    }

    public UserDTO update(UserDTO userDTO){
        User user=userRepository.findById(userDTO.getId()).orElseThrow(EntityNotFoundException::new);
        user.setUsername(userDTO.getUsername());
        if(!userDTO.getPassword().equals("")){
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }
        return UserBuilder.toUserDTO(userRepository.save(user));
    }
}
