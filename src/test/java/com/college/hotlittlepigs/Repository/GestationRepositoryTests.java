package com.college.hotlittlepigs.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.gestation.Gestation;
import com.college.hotlittlepigs.gestation.GestationRepository;
import com.college.hotlittlepigs.matrix.Matrix;
import com.college.hotlittlepigs.matrix.MatrixRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;

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
public class GestationRepositoryTests {
    
    @Autowired private BoxRepository boxRepository;
    @Autowired private MatrixRepository matrixRepository;
    @Autowired private GestationRepository gestationRepository;

    @Test
    public void saveTest(){
        Optional<Box> requestBox = boxRepository.findById(1L);
        Box box = requestBox.get();
        
        Optional<Matrix> requestMatrix = matrixRepository.findById(1L);
        Matrix matrix = requestMatrix.get();
        Gestation gestation = new Gestation(null, null, null, new Date(), null, 4, null, true, box, matrix);
        Gestation createdGestation = gestationRepository.save(gestation);
        assertThat(createdGestation.getId()).isEqualTo(1L);    
    }

    @Test
    public void findAllTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Gestation> page = gestationRepository.findAll(prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void getByIdTest(){
        Optional<Gestation> result = gestationRepository.findById(1L);
        Gestation gestation = result.get();
        assertThat(gestation.getId()).isEqualTo(1);
    }

    @Test
    public void findAllByBoxIdTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Gestation> page = gestationRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void findAllByMatrixIdTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Gestation> page = gestationRepository.findAllByMatrixId(1L, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }
}