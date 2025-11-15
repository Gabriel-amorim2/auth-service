package banking.platform.bankingPlatform.dto;

import banking.platform.bankingPlatform.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
