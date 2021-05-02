package com.college.hotlittlepigs.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class PageModel<T> implements Serializable {
  // TODO Manual constructor since Kotlin does not recognize @AllArgsConstructor
  public PageModel(int totalElements, int pageSize, int totalPages, List<T> elements) {
    this.totalElements = totalElements;
    this.pageSize = pageSize;
    this.totalPages = totalPages;
    this.elements = elements;
  }

  private static final long serialVersionUID = 1L;

  private int totalElements;
  private int pageSize;
  private int totalPages;
  private List<T> elements;
}
