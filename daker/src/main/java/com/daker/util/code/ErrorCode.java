package com.daker.util.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements BaseErrorCode{
    LoginIdDuplicate(HttpStatus.BAD_REQUEST, "400", "로그인 아이디가 중복되었습니다."),
    EmailDuplicate(HttpStatus.BAD_REQUEST, "400", "이메일이 중복되었습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "잘못된 요청입니다."),
    UNAUTHORIZED_401(HttpStatus.UNAUTHORIZED, "COMMON401", "권한이 없습니다"),
    FORBIDDEN_403(HttpStatus.FORBIDDEN, "COMMON403", "접근이 금지되었습니다"),
    USER_NOT_FOUND_404(HttpStatus.NOT_FOUND, "COMMON404", "요청한 유저를 찾을 수 없습니다"),
    TEAM_NOT_FOUND_404(HttpStatus.NOT_FOUND, "COMMON404", "요청한 팀을 찾을 수 없습니다"),
    NOT_FOUND_404(HttpStatus.NOT_FOUND, "COMMON404", "요청한 자원을 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR_500(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 내부 오류가 발생했습니다"),
    TEAM_MEMBER_EXCEEDED(HttpStatus.CONFLICT, "COMMON409", "충돌이 발생했습니다."),
    BEFORE_TEMPERATURE_SET(HttpStatus.BAD_REQUEST, "400", "이미 측정했던 유저입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}