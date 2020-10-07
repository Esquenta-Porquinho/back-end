package com.college.hotlittlepigs.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.DEFAULT)
@SpringBootTest
public class BoxRepositoryTests {
    
    @Autowired private BoxRepository boxRepository;

    @Test
    public void saveTest(){
        Box newBox = new Box(null, 2, "Piso", "4", true);
        Box createdBox = boxRepository.save(newBox);

        assertThat(createdBox.getId()).isEqualTo(1L);    
    }

    @Test
    public void findAllTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Box> page = boxRepository.findAll(prm.toSpringPageRequest());
        
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void getByIdTest() {
        Optional<Box> result = boxRepository.findById(1L);
        Box box = result.get();

        assertThat(box.getNumber()).isEqualTo(2);
    }

}
