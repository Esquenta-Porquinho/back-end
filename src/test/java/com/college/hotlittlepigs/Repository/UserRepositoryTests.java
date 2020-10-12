package com.college.hotlittlepigs.Repository;

import java.util.List;
import java.util.Optional;

import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.user.User;
import com.college.hotlittlepigs.user.UserRepository;
import com.college.hotlittlepigs.user.enums.Role;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.DEFAULT)
@SpringBootTest
public class UserRepositoryTests {
    @Autowired private UserRepository userRepository;

    @Test
    public void saveTest(){
        User user = new User(null, "Jean", "jeanjms.1999@gmail.com", "123", Role.ADMIN, null);
        User createduser = userRepository.save(user);

        assertThat(createduser.getId()).isEqualTo(1L);    
    }

    @Test
    public void updateTest(){

        User user = new User(1L, "Jean Moraes", "jeanjms.1999@gmail.com", "123", Role.ADMIN, null);
        User updateUser = userRepository.save(user);
                
        assertThat(updateUser.getName()).isEqualTo("Jean Moraes");
    }

    @Test
    public void getByIdTest(){

        Optional<User> result = userRepository.findById(1L);
        User user = result.get();

        assertThat(user.getPassword()).isEqualTo("123");
    }

    @Test
    public void listAllTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<User> page = userRepository.findAll(prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void listAllByRole(){
        List<User> users = userRepository.findAllByRole(Role.ADMIN);
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void findAllNotAdmin(){
        PageRequestModel prm = new PageRequestModel();
        Page<User> page = userRepository.findAllNotAdmin(Role.ADMIN, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    };

    @Test
    public void loginTest(){
        Optional<User> result = userRepository.findByEmail("jeanjms.1999@gmail.com");
        User loggedUser = result.get();
        assertThat(loggedUser.getId()).isEqualTo(1L);
    }

    @Test
    public void updateRole(){
        int affectedRows = userRepository.updateRole(1L, Role.MANAGER);
        assertThat(affectedRows).isEqualTo(1);
    }

}