package com.sihoily.tilboard.member.application.result;

import com.sihoily.tilboard.member.domain.Member;
import com.sihoily.tilboard.member.domain.Token;

public record LoginResult (Member member, Token token) { }
