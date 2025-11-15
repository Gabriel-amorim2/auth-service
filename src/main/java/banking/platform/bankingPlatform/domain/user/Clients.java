package banking.platform.bankingPlatform.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Table(name = "clients")
@Entity(name = "clients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Clients implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Clients(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return login;
    }
}
