package banking.platform.bankingPlatform.domain.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum UserRole {
    ADMIN {
        @Override
        public List<SimpleGrantedAuthority> getAuthorities() {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_TECNICO"),
                    new SimpleGrantedAuthority("ROLE_MANAGER")
            );
        }
    },
    TECNICO {
        @Override
        public List<SimpleGrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_TECNICO"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
    },
    GESTOR {
        @Override
        public List<SimpleGrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
    },
    USER {
        @Override
        public List<SimpleGrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    };

    public abstract List<SimpleGrantedAuthority> getAuthorities();
    }

