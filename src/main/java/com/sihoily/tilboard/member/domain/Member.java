package com.sihoily.tilboard.member.domain;

public record Member(Long id, String email, String password, String nickname, Role role) {
}
