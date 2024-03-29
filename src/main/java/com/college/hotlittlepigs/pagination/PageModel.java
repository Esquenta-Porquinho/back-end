package com.college.hotlittlepigs.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageModel<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private int totalElements;
  private int pageSize;
  private int totalPages;
  private List<T> elements;
}
