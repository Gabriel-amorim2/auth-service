package banking.platform.bankingPlatform.service;

import banking.platform.bankingPlatform.domain.user.Clients;
import banking.platform.bankingPlatform.dto.AuthenticationDTO;
import banking.platform.bankingPlatform.dto.TokenOptDTO;
import banking.platform.bankingPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private ServiceMfa serviceMfa;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TokenService tokenService;


    public void login(AuthenticationDTO authDTO){
        var userNamePasswoard = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePasswoard);
        serviceMfa.sendEmail(authDTO.email(),authDTO.password());

    }
    public String verifyOtp(TokenOptDTO request){
        boolean valid = serviceMfa.validOtp(
                request.email(),
                request.opt()
        );

        if(!valid)
            throw new RuntimeException("OTP inválido");


        Clients client = (Clients) userRepository.findByEmail(request.email());


        return tokenService.generetedToken(client);
    }

}
