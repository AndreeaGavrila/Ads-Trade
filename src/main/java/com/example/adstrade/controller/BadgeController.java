package com.example.adstrade.controller;

import com.example.adstrade.enums.BadgeType;
import com.example.adstrade.model.Badge;
import com.example.adstrade.model.dto.BadgeDto;
import com.example.adstrade.model.dto.UserDto;
import com.example.adstrade.model.requests.AddBadgeToUsersRequest;
import com.example.adstrade.repository.BadgeRepository;
import com.example.adstrade.service.BadgeService;
import com.example.adstrade.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/badge")
public class BadgeController {

    private final BadgeService badgeService;
    private final UserService userService;
    private final BadgeRepository badgeRepository;

    @Autowired
    public BadgeController(BadgeService badgeService, UserService userService,
                           BadgeRepository badgeRepository) {
        this.badgeService = badgeService;
        this.userService = userService;
        this.badgeRepository = badgeRepository;
    }

    @ApiOperation(value = "Find a badge by ID", notes = "The badge must exist.")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<BadgeDto> findBadgeById(@PathVariable("id") Long id){
        Optional<Badge> optionalBadge = badgeService.findBadgeById(id);
        if(optionalBadge.isPresent()){
            BadgeDto badgeDto = BadgeDto.convertEntityToDto(optionalBadge.get());
            return new ResponseEntity<>(badgeDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find all badges", notes = "Display all existing badges.")
    @GetMapping("/get-all")
    public ResponseEntity<List<BadgeDto>> findAllBadges(){
        return new ResponseEntity<>(badgeService.findAllBadges(), HttpStatus.OK);
    }


    @ApiOperation(value = "Find all badges by Name", notes = "Display all existing badges by a giving name.")
    @GetMapping("/get-all-by-name")
    public ResponseEntity<List<BadgeDto>> findAllBadgesByName(@RequestParam BadgeType name){
        return new ResponseEntity<>(badgeService.findAllBadgesByName(name), HttpStatus.OK);
    }


    @ApiOperation(value = "Add a badge to users", notes = "Reward a user with a badge.")
    @PostMapping("/add-badge-to-users")
    public ResponseEntity<UserDto> AddBadgeToUsers(@RequestBody AddBadgeToUsersRequest request) {
        UserDto result = userService.addBadgeToUsers(request);
        if(result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
