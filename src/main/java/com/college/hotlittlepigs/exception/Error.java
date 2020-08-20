package com.college.hotlittlepigs.exception;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Error implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private int code;
    private String msg;
    private Date date;
    private List<String> errors;
}