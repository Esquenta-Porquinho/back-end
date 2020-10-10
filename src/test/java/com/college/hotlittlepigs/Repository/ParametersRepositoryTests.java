package com.college.hotlittlepigs.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.parameters.Parameters;
import com.college.hotlittlepigs.parameters.ParametersRepository;

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
public class ParametersRepositoryTests {
    
    @Autowired private BoxRepository boxRepository;
    @Autowired private ParametersRepository parametersRepository;

    @Test
    public void saveTest(){
        Optional<Box> requestBox = boxRepository.findById(1L);
        Box box = requestBox.get();
        
        Parameters parameters = new Parameters(null, 28.00, 23.00, 28.00, 23.00, 28.00, 23.00, 1.0, false, box);
        Parameters createdParameters = parametersRepository.save(parameters);
        assertThat(createdParameters.getId()).isEqualTo(1L);    
    }

    @Test
    public void findAllTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Parameters> page = parametersRepository.findAll(prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void getByIdTest(){
        Optional<Parameters> result = parametersRepository.findById(1L);
        Parameters parameters = result.get();
        assertThat(parameters.getId()).isEqualTo(1);
    }

    @Test
    public void findAllByBoxIdTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Parameters> page = parametersRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void findActiveByWeekByBoxTest(){
        Optional<Parameters> result = parametersRepository.findActiveByWeekByBox(1L, 1.0);
        Parameters parameters = result.get();
        assertThat(parameters.getId()).isEqualTo(1);
    }
}