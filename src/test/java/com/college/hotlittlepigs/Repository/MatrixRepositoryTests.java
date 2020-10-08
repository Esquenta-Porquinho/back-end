package com.college.hotlittlepigs.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.college.hotlittlepigs.matrix.Matrix;
import com.college.hotlittlepigs.matrix.MatrixRepository;
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
public class MatrixRepositoryTests {
    
    @Autowired private MatrixRepository matrixRepository;

    @Test
    public void saveTest(){
        Matrix newMatrix = new Matrix(null, 39, true, null);
        Matrix createdMatrix = matrixRepository.save(newMatrix);

        assertThat(createdMatrix.getId()).isEqualTo(1L);    
    }

    @Test
    public void findAllTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Matrix> page = matrixRepository.findAll(prm.toSpringPageRequest());
        
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void getByIdTest() {
        Optional<Matrix> result = matrixRepository.findById(1L);
        Matrix matrix = result.get();

        assertThat(matrix.getNumber()).isEqualTo(39);
    }



}
