package com.backEnd.tecback.controller;


import com.backEnd.tecback.auth.TokenService;
import com.backEnd.tecback.dto.AuthenticationDTO;
import com.backEnd.tecback.dto.CadastroDTO;
import com.backEnd.tecback.dto.LoginResponseDTO;
import com.backEnd.tecback.models.User;
import com.backEnd.tecback.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),data.password());

        try {

            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToke((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (Exception e) {

            System.out.println("Authentication failed for user: " + data.login());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastro(@RequestBody @Valid CadastroDTO data) {
    if(this.userRepository.findByLogin(data.login()) !=  null) return ResponseEntity.badRequest().build();

    String senhaCriptografada = new BCryptPasswordEncoder().encode(data.password());

    User novoUsuario = new User(data.login(), senhaCriptografada, data.role());

    this.userRepository.save(novoUsuario);

    return ResponseEntity.ok().build();

    }

}
