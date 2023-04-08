package xiaoqiangZzz.api.dealHebut.service;

import xiaoqiangZzz.api.dealHebut.entity.Goods;

import java.util.List;

public interface GoodsService {
  Goods add(Goods goods);

  Goods getById(Long id);

  List<Goods> getByUserId(Long id);

  List<Goods> getAll();
}
