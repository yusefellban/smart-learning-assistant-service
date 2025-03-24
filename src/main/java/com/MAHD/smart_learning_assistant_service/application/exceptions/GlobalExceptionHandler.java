/***
 * I do not use this class because the Swagger UI static resources (e.g., swagger-ui/index.html)
 * trigger a NoResourceFoundException which is intercepted by our global exception handler.
 * This interception prevents the Swagger UI from loading correctly. Instead, we rely on Spring Boot's
 * default static resource handling to serve Swagger UI without interference.
 */
//package com.MAHD.smart_learning_assistant_service.application.exceptions;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) throws NoResourceFoundException {
//        /// To handel the swagger to be run
//        if (ex instanceof NoResourceFoundException && ex.getMessage() != null
//                && ex.getMessage().contains("swagger-ui/index.html")) {
//            throw (NoResourceFoundException) ex;
//        }
//        logger.error("Unhandled Exception: {}", ex.getMessage(), ex);
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        response.put("message", "Something went wrong!\n" + ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<Map<String, Object>> userNotFoundException(UserNotFoundException ex) {
//        logger.warn("UserNotFoundException: {}", ex.getMessage());
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", HttpStatus.NOT_FOUND.value());
//        response.put("message", "Something went wrong!\nUser not found!\n" + ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(AgentNotRunningException.class)
//    public ResponseEntity<Map<String, Object>> agentNotRunningException(AgentNotRunningException ex) {
//        logger.error("AgentNotRunningException: {}", ex.getMessage());
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
//        response.put("message", "Something went wrong!\nAgent not running!\n" + ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
//    }
//}
