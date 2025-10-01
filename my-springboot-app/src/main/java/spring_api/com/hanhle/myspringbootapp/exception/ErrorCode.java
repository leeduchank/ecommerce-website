package spring_api.com.hanhle.myspringbootapp.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public enum ErrorCode {
    USER_NOTFOUND(777,"user doesn't existed"),

    USER_UNKNOWN(666,"Sth Wrong"),
    USER_EXISTED(1001,"user existed"),
    USERNAME_INVALID(1002,"username must at least 3 character"),
    PASSWORD_INVALID(1003,"password must at least 8 character")

    ;

    ErrorCode(int code, String message) {
        this.code = code;
        Message = message;
    }

    private int code;
    private String Message;

    public void setMessage(String message) {
        Message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return Message;
    }

    public void setCode(int code){
        this.code = code;
    }
}
