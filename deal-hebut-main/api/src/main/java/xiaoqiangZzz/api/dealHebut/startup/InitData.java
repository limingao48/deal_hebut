package xiaoqiangZzz.api.dealHebut.startup;

import xiaoqiangZzz.api.dealHebut.entity.User;
import xiaoqiangZzz.api.dealHebut.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 系统数据初始化.
 */
@Component
public class InitData {
  private static final Logger logger = LoggerFactory.getLogger(InitData.class);

  private UserRepository userRepository;

  private String Username = "13131693956";
  private String Password = "123456";


  public InitData(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void postConstruct() {

    List<User> users = this.userRepository.findAll();

    if (!users.isEmpty()) {
      return;
    }

    User user1 = new User();
    user1.setUsername(this.Username);
    user1.setPassword(this.Password);
    user1.setPetName("用户1号");
    users.add(user1);
    User user2 = new User();
    user2.setUsername("13900000000");
    user2.setPassword(this.Password);
    user2.setPetName("用户2号");
    users.add(user2);
    this.userRepository.saveAll(users);
  }
}
