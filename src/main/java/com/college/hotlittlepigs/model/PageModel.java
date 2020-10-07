package com.college.hotlittlepigs.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageModel<T> implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private int totalElements;
    private int pageSize;
    private int totalPages;
	private List<T> elements;
}