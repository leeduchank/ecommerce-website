package spring_api.com.hanhle.myspringbootapp.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
public enum ErrorCode {
    USER_NOTFOUND(777,"user doesn't existed",HttpStatus.NOT_FOUND),
    UNAUTHORIZE (999,"You don't have permission",HttpStatus.FORBIDDEN),
    USER_UNKNOWN(666,"Sth Wrong",HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001,"user existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1002,"username must at least 3 character",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003,"password must at least 8 character",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED (1004,"unauthenticated!",HttpStatus.UNAUTHORIZED),
    PASSWORD_UNKNOWN (1004,"Password incorrect!",HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message,HttpStatusCode statusCode) {
        this.code = code;
        Message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String Message;
    private HttpStatusCode statusCode;


}
