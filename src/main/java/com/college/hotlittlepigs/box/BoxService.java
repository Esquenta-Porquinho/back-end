package com.college.hotlittlepigs.box;

import com.college.hotlittlepigs.box.dto.BoxUpdateDTO;
import com.college.hotlittlepigs.box.exception.BoxNotFoundException;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.parameters.Parameters;
import com.college.hotlittlepigs.parameters.ParametersService;
import com.college.hotlittlepigs.parameters.expcetion.ParametersNotFoundException;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BoxService {

  private final BoxRepository repository;
  private final ParametersService parametersService;

  public Box save(Box box) {
    return repository.save(box);
  }

  public Box getById(Long id) {
    var box = repository.findById(id);
    return box.orElseThrow(BoxNotFoundException::new);
  }

  public Box getByNumber(int number) {
    var box = repository.findBoxByNumberAndStatus(number, true);
    return box.orElseThrow(BoxNotFoundException::new);
  }

  public PageModel<Box> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Box updateBox(Long id, BoxUpdateDTO box) {
    var updatableBox = this.getById(id);
    updatableBox.setController(box.getController());
    updatableBox.setArea(box.getArea());
    return save(updatableBox);
  }

  public Box updateStatus(Long id, Boolean status) {
    var box = this.getById(id);
    box.setStatus(status);
    return save(box);
  }

  public Parameters getParameters(int number) {
    var box = getByNumber(number);
    var gestations = box.getGestations();

    if (gestations.isEmpty()) throw new ParametersNotFoundException();

    var gestation = gestations.get(gestations.size() - 1);
    var date = gestation.getEffectiveParturition();

    var today = DateTime.now();
    var birthDate =
        new DateTime((date.getYear() + 1900), (date.getMonth() + 1), date.getDate(), 0, 0, 0);

    var days = Days.daysBetween(birthDate, today);
    Double week = Math.ceil(days.getDays() / 7.0);

    return parametersService.geyByActiveBox(box, week);
  }
}
