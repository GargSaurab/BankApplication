package com.app.Dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {

   private LocalDateTime timeStamp;
   private String  message;
  //  private int status;
  
  public ApiResponse(String  message)
  {
    this.timeStamp = LocalDateTime.now();
    this.message = message;
  }
  
  // for the cases when specific status will be needed
  //  public ApiResponse(String  message, int status)
  //  {
  //    this.timeStamp = LocalDateTime.now();
  //    this.message = message;
  //    this.status = status;
  //  }

}
