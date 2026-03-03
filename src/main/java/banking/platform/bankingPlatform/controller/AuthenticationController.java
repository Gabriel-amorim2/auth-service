package banking.platform.bankingPlatform.controller;

import banking.platform.bankingPlatform.domain.user.Clients;
import banking.platform.bankingPlatform.domain.user.UserRole;
import banking.platform.bankingPlatform.dto.AuthenticationDTO;
import banking.platform.bankingPlatform.dto.LoginResponseDTO;
import banking.platform.bankingPlatform.dto.RegisterDTO;
import banking.platform.bankingPlatform.infra.security.TokenService;
import banking.platform.bankingPlatform.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authDTO) {
        var userNamePasswoard = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePasswoard);
        var token = tokenService.generetedToken((Clients) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().body("existing user");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Clients newClients = new Clients(data.email(), encryptedPassword, UserRole.USER);
        userRepository.save(newClients);

        return ResponseEntity.status(HttpStatus.CREATED).body("user created: " + data.email());

    }

    @PostMapping("/create-admin")
    public ResponseEntity registerAdmin(@RequestBody @Valid RegisterDTO dto) {
        if (userRepository.findByEmail(dto.email()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This record already exists in the database: " + dto.email());

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Clients newClients = new Clients(dto.email(), encryptedPassword, dto.role());
        userRepository.save(newClients);

        return ResponseEntity.status(HttpStatus.CREATED).body("ADMIN created: " + dto.email());

    }

}
