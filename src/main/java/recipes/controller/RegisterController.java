package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.model.User;
import recipes.repositories.UserRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @PostMapping
    public ResponseEntity register(@RequestBody @Valid User user) {
        UserDetails userFromDb = userRepo.findByEmail(user.getUsername());
        if (userFromDb != null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}
