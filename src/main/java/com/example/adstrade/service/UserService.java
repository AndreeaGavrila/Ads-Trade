package com.example.adstrade.service;

import com.example.adstrade.enums.Role;
import com.example.adstrade.model.Badge;
import com.example.adstrade.model.requests.AddBadgeToUsersRequest;
import com.example.adstrade.model.requests.RegisterUserRequest;
import com.example.adstrade.model.User;
import com.example.adstrade.model.dto.UserDto;
import com.example.adstrade.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.dto.UserDto.convertEntityToDto;
import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BadgeService badgeService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, BadgeService badgeService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.badgeService = badgeService;
    }

    public UserDto registerUser(@NotNull RegisterUserRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDescription(request.getDescription());
        user.setRole(Role.ROLE_USER);
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        return convertEntityToDto(userRepository.save(user));
    }

    @Transactional
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserDto findUserByEmailDto(String email){
        Optional<User> user = findUserByEmail(email);
        return user.map(UserDto::convertEntityToDto).orElse(null);
    }

    @Transactional
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    public UserDto findUserByIdDto(Long id){
        Optional<User> user = findUserById(id);
        return user.map(UserDto::convertEntityToDto).orElse(null);
    }

    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public List<UserDto> findAllUsersDto() {
        List<User> allUsers = findAllUsers();
        return allUsers.stream().map(UserDto::convertEntityToDto).collect(toList());
    }

    public void insertUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public UserDto addBadgeToUsers(@NotNull AddBadgeToUsersRequest request) {
        Optional<User> optionalUser = findUserById(request.getUserId());
        Optional<Badge> optionalBadge = badgeService.findBadgeById(request.getBadgeId());

        if (optionalUser.isPresent() && optionalBadge.isPresent()) {
            User user = optionalUser.get();
            Badge badge = optionalBadge.get();

            Optional<Badge> alreadyAddedBadge = user.getBadges().stream().filter(existingBadge ->
                    existingBadge.getId().equals(badge.getId())).findAny();

            if (alreadyAddedBadge.isEmpty()) {
                user.addBadgeToUsersList(badge);
                return UserDto.convertEntityToDto(userRepository.save(user));
            }
        }
        return null;
    }

}
