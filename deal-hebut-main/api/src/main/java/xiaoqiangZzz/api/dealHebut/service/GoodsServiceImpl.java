package xiaoqiangZzz.api.dealHebut.service;

import org.springframework.stereotype.Service;
import xiaoqiangZzz.api.dealHebut.entity.Goods;
import xiaoqiangZzz.api.dealHebut.repository.GoodsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
  private final GoodsRepository goodsRepository;
  private final UserService userService;

  public GoodsServiceImpl(GoodsRepository goodsRepository,
                          UserService userService) {
    this.goodsRepository = goodsRepository;
    this.userService = userService;
  }

  @Override
  public Goods add(Goods goods) {
    goods.setCreateUser(this.userService.getCurrentUser());
    return this.goodsRepository.save(goods);
  }

  @Override
  public Goods getById(Long id) {
    return this.goodsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("找不到相关商品"));
  }

  @Override
  public List<Goods> getByUserId(Long id) {
    List<Goods> goods =  this.goodsRepository.findAllByCreateUserId(id);

    return goods;

  }

    @Override
    public void delete(Long id) {
        this.goodsRepository.deleteById(id);
    }

    @Override
  public List<Goods> getAll() {
    return this.goodsRepository.findAllByOrderByIdDesc();
  }
}
