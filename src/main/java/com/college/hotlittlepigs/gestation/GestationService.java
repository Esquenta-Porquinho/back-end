package com.college.hotlittlepigs.gestation;

import java.util.Optional;

import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GestationService {
    private GestationRepository gestationRepository;

    public Gestation save(Gestation gestation) {
        Gestation newGestation = gestationRepository.save(gestation);
        return newGestation;
    }

    public PageModel<Gestation> listAll(PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<Gestation> page = gestationRepository.findAll(pageable);

        PageModel<Gestation> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public Gestation getById(Long id) {
        Optional<Gestation> result = gestationRepository.findById(id);
        if(!result.isPresent()) throw new NotFoundException("Gestation not found !!");

        return result.get();
    }

    public Gestation updateGestation(Long id, Gestation gestation){
        gestation.setId(id);
        Gestation updatedGestation = gestationRepository.save(gestation);
        return updatedGestation;
    }

    public Gestation updateStatus(Long id, Boolean status){
        Gestation gestation = this.getById(id);
        gestation.setStatus(status);
        Gestation newGestation = this.save(gestation);
        return newGestation;
    }

    public PageModel<Gestation> listAllByMatrix(Long id, PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<Gestation> page = gestationRepository.findAllByMatrixId(id, pageable);

        PageModel<Gestation> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public PageModel<Gestation> listAllByBox(Long id, PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<Gestation> page = gestationRepository.findAllByBoxId(id, pageable);

        PageModel<Gestation> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    

}
