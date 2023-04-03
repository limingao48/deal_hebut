package xiaoqiangZzz.api.dealHebut.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import xiaoqiangZzz.api.dealHebut.entity.Chat;
import xiaoqiangZzz.api.dealHebut.entity.User;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long>, JpaSpecificationExecutor<Chat> {
  Chat findByUsersContainingAndUsersContaining(User user, User user1);

  List<Chat> findAllByUsersContaining(User user);
}
