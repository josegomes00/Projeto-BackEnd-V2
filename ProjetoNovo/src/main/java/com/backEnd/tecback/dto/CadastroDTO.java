package com.backEnd.tecback.dto;

import com.backEnd.tecback.enuns.UserRole;

public record CadastroDTO(String login, String password, UserRole role) {
}
