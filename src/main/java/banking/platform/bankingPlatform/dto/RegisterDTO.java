package banking.platform.bankingPlatform.dto;

import banking.platform.bankingPlatform.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank @Email String email, String password, UserRole role) {
}
