package com.app.Dto;

import java.time.LocalDateTime;

import com.app.Dto.TrscType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDto {
   
    private int trscId;
    private LocalDateTime time;
    private TrscType type;

}
