package com.college.hotlittlepigs.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageRequestModel {
  private int page = 0;
  private int size = 10;
  private String sort = "";

  public PageRequestModel(Map<String, String> params) {
    if (params.containsKey("page")) page = Integer.parseInt(params.get("page"));
    if (params.containsKey("size")) size = Integer.parseInt(params.get("size"));
    if (params.containsKey("sort")) sort = params.get("sort");
  }

  public PageRequest toSpringPageRequest() {
    List<Order> orders = new ArrayList<>();
    var properties = sort.split(",");
    for (var prop : properties) {
      if (prop.trim().length() > 0) {
        var column = prop.trim();

        if (column.startsWith("-")) {
          column = column.replace("-", "").trim();
          orders.add(Order.desc(column));
        } else {
          orders.add(Order.asc(column));
        }
      }
    }
    return PageRequest.of(page, size, Sort.by(orders));
  }
}
