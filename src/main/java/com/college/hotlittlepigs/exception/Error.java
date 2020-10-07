package com.college.hotlittlepigs.exception;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Error{
    
    private int code;
    private String msg;
    private Date date;
    private List<String> errors;

}