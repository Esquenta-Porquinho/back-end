package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.user.User;
import com.college.hotlittlepigs.user.UserRepository;
import com.college.hotlittlepigs.user.enums.Role;
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
public class UserRepositoryTests {
  @Autowired private UserRepository userRepository;

  @Test
  public void saveTest() {
    var user = new User(null, "Jean", "jeanjms.1999@gmail.com", "123", Role.ADMIN, null);
    var createduser = userRepository.save(user);

    assertThat(createduser.getId()).isEqualTo(1L);
  }

  @Test
  public void updateTest() {

    var user = new User(1L, "Jean Moraes", "jeanjms.1999@gmail.com", "123", Role.ADMIN, null);
    var updateUser = userRepository.save(user);

    assertThat(updateUser.getName()).isEqualTo("Jean Moraes");
  }

  @Test
  public void getByIdTest() {

    var result = userRepository.findById(1L);
    var user = result.get();

    assertThat(user.getPassword()).isEqualTo("123");
  }

  @Test
  public void listAllTest() {
    var prm = new PageRequestModel();
    var page = userRepository.findAll(prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(2);
  }

  @Test
  public void listAllByRole() {
    var users = userRepository.findAllByRole(Role.ADMIN);
    assertThat(users.size()).isEqualTo(1);
  }

  @Test
  public void findAllByRoleIsNot() {
    var prm = new PageRequestModel();
    var page = userRepository.findAllByRoleIsNot(Role.ADMIN, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void loginTest() {
    var result = userRepository.findByEmail("jeanjms.1999@gmail.com");
    var loggedUser = result.get();
    assertThat(loggedUser.getId()).isEqualTo(1L);
  }

  @Test
  public void updateRole() {
    var affectedRows = userRepository.updateRole(1L, Role.MANAGER);
    assertThat(affectedRows).isEqualTo(1);
  }
}
