package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder
@SpringBootTest
public class BoxRepositoryTests {

  @Autowired private BoxRepository boxRepository;

  @Test
  public void saveTest() {
    var newBox = new Box(null, 2, "Piso", "4", true, null, null, null, null);
    var createdBox = boxRepository.save(newBox);

    assertThat(createdBox.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllTest() {
    var prm = new PageRequestModel();
    var page = boxRepository.findAll(prm.toSpringPageRequest());

    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void getByIdTest() {
    var result = boxRepository.findById(1L);
    var box = result.get();

    assertThat(box.getNumber()).isEqualTo(2);
  }

  @Test
  public void findByNumberByStatus() {
    var result = boxRepository.findBoxByNumberAndStatus(2, true);
    var box = result.get();

    assertThat(box.getId()).isEqualTo(1L);
  }
}
