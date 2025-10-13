package spring_api.com.hanhle.myspringbootapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring_api.com.hanhle.myspringbootapp.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> hanldingRuntimeException(Exception e) {


        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                .code(ErrorCode.USER_UNKNOWN.getCode())
                .message(ErrorCode.USER_UNKNOWN.getMessage())
                .build());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> hanldingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.<String>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> hanldingAuthorizeException(AuthorizationDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZE;
        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.<String>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingValidException(MethodArgumentNotValidException e)
    {
        String enumkey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumkey);
        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());    }
}
