package com.akash.rest.webservices.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    private UserDaoService service;

    public  UserResource(UserDaoService service)
    {
        this.service=service;
    }
    @GetMapping("/users")
    public List<User> retreiveAllUsers(){
        return service.findAll();
    }
    @GetMapping("/users/{id}")
    public User retreiveUser(@PathVariable int id)
    {
        User user=service.findOne(id);
        if(user == null)
            throw new UserNotFoundException("id:"+id);
        return  user;
    }
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
   User savedUser= service.save(user);
    URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
    return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
       service.deleteById(id);

    }
}
