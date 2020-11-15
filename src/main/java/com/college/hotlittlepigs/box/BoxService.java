package com.college.hotlittlepigs.box;

import com.college.hotlittlepigs.box.dto.BoxUpdateDTO;
import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.gestation.Gestation;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.parameters.Parameters;
import com.college.hotlittlepigs.parameters.ParametersService;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoxService {

  private final BoxRepository repository;
  private final ParametersService parametersService;

  public Box save(Box box) {
    return repository.save(box);
  }

  public Box getById(Long id) {
    Optional<Box> result = repository.findById(id);
    if (result.isEmpty()) throw new NotFoundException("Box not found !!");
    return result.get();
  }

  public Box getByNumber(int number) {
    Optional<Box> result = repository.findBoxByNumberAndStatus(number, true);
    if (result.isEmpty()) throw new NotFoundException("Box not found !!");
    return result.get();
  }

  public PageModel<Box> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Box> page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Box updateBox(Long id, BoxUpdateDTO box) {
    Box updatableBox = this.getById(id);
    updatableBox.setController(box.getController());
    updatableBox.setArea(box.getArea());
    return save(updatableBox);
  }

  public Box updateStatus(Long id, Boolean status) {
    Box box = this.getById(id);
    box.setStatus(status);
    return save(box);
  }

  public Parameters getParameters(int number) {
    Box box = getByNumber(number);
    List<Gestation> gestations = box.getGestations();

    if (gestations.isEmpty()) throw new NotFoundException("Parameters not found !!");

    Gestation gestation = gestations.get(gestations.size() - 1);
    Date date = gestation.getEffectiveParturition();

    DateTime today = DateTime.now();
    DateTime birthDate =
        new DateTime((date.getYear() + 1900), (date.getMonth() + 1), date.getDate(), 0, 0, 0);

    Days days = Days.daysBetween(birthDate, today);
    Double week = Math.ceil(days.getDays() / 7.0);

    return parametersService.getParametersByActiveByBox(box, week);
  }
}
