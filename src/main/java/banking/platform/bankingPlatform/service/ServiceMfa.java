package banking.platform.bankingPlatform.service;


import banking.platform.bankingPlatform.dto.TokenOptDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class ServiceMfa {


    private final RedisTemplate<String,String> redisTemplate;
    private final EmailService emailService;


    public ServiceMfa(RedisTemplate<String, String> redisTemplate, EmailService emailService) {
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
    }

    public void sendEmail(String email, String password){
        var code = genereteCode(email);
        emailService.sendEmail(email,code);
    }

    private String genereteCode(String email){
        String code = String.valueOf(new Random().nextInt(900000)+100000);

        String key = "OTP:" + email;
            redisTemplate.opsForValue().set(
                  key,
                  code,
                  Duration.ofMinutes(5)
        );
        return code;
    }

    public boolean validOtp(String email, String code){
        String key = "OTP:" + email;

        String storedOtp = redisTemplate.opsForValue().get(key);
        if(storedOtp == null)
            return false;
        redisTemplate.delete(key);

        return storedOtp.equals(code);
    }

    }




