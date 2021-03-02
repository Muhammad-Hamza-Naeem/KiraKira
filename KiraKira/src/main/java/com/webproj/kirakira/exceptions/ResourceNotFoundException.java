package com.webproj.kirakira.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Resource not found exception.
 *
 * @author muneeb
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

 

/**
   * Instantiates a new Resource not found exception.
   *
   * @param message the message
   */
  public ResourceNotFoundException(String message) {
    super(message);
  
    
  }
}
