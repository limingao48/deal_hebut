package xiaoqiangZzz.api.dealHebut.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import xiaoqiangZzz.api.dealHebut.entity.Goods;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {
  List<Goods> findAllByOrderByIdDesc();
  List<Goods> findAllByCreateUserId(Long id);
}
