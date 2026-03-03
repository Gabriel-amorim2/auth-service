package banking.platform.bankingPlatform.repository;

import banking.platform.bankingPlatform.domain.user.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Clients,String> {
     UserDetails findByEmail(String login);
}
