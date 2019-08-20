package com.cjbs.demo.web.rest;


import com.cjbs.demo.domain.User;
import com.cjbs.demo.service.UserService;
import com.cjbs.demo.service.redis.RedisTemplateService;
import com.cjbs.demo.web.dto.UserDTO;
import com.codahale.metrics.annotation.Timed;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


/**
 * @author shj
 */
@RestController
@RequestMapping("/api")
public class RedisController {

    private final Logger logger = LoggerFactory.getLogger(RedisController.class);

    private RedisTemplateService redisTemplateService;

    private UserService userService;

    public RedisController(RedisTemplateService redisTemplateService, UserService userService) {
        this.redisTemplateService = redisTemplateService;
        this.userService= userService;
    }

    /**
     * testRedis
     */
    @GetMapping("/testRedis")
    @Timed
    public void testRedis() {
        redisTemplateService.test();
    }

    /**
     * testRedis存储序列化对象
     */
    @GetMapping("/testSerializableRedis")
    @Timed
    public void testSeriaRedis() {
        redisTemplateService.testSerializableRedis();
    }

    /**
     * 测试Redis缓存   find
     * @param id id
     * @return User
     */
    @GetMapping("/testCacheRedis")
    @Timed
    public ResponseEntity<Optional<User>> testCacheRedis(@RequestParam String id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**
     * 测试Redis缓存   update
     * @param userDTO userDTO
     * @return User
     */
    @PostMapping("/testRedisUpdate")
    @Timed
    public ResponseEntity<User> testRedisUpdate(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 测试Redis缓存   delete
     * @param id id
     */
    @DeleteMapping("/testRedisDelete")
    @Timed
    public void testRedisDelete(@RequestParam String id) {
        userService.deleteUser(id);
    }
}
