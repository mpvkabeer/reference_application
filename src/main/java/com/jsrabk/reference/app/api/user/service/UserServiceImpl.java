package com.jsrabk.reference.app.api.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jsrabk.reference.app.api.model.Role;
import com.jsrabk.reference.app.api.model.Status;
import com.jsrabk.reference.app.api.model.User;
import com.jsrabk.reference.app.api.role.repository.RoleRepository;
import com.jsrabk.reference.app.api.user.dto.UserDTO;
import com.jsrabk.reference.app.api.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

	@Override
	public User getById(long id) {
		return userRepository.findById(id).get();
	}    
    
    @Override
    public void saveUser(UserDTO userDto) {
        User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Status userStatus = new Status();
        userStatus.setId(1);
        user.setStatus(userStatus);

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDTO convertEntityToDto(User user){
        UserDTO userDto = new UserDTO();
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    @Override
    public List<User> list() {
        List<User> users = userRepository.findAll();
        return users;
    }   
    
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

}
