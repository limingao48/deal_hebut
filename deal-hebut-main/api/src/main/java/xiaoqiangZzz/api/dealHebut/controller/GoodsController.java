package xiaoqiangZzz.api.dealHebut.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;
import xiaoqiangZzz.api.dealHebut.entity.Goods;
import xiaoqiangZzz.api.dealHebut.service.GoodsService;

import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {
  private final GoodsService goodsService;

  public GoodsController(GoodsService goodsService) {
    this.goodsService = goodsService;
  }

  @PostMapping("add")
  @JsonView(GetByIdJsonView.class)
  public Goods add(@RequestBody Goods goods) {
    return this.goodsService.add(goods);
  }

  @GetMapping("getById/{id}")
  @JsonView(GetByIdJsonView.class)
  public Goods getById(@PathVariable Long id) {
    return this.goodsService.getById(id);
  }

  @GetMapping("getByUserId/{id}")
  @JsonView(GetByUserJsonView.class)
  public List<Goods> getByUserId(@PathVariable Long id) {
    return this.goodsService.getByUserId(id);
  }

  @GetMapping("getAll")
  @JsonView(GetAllJsonView.class)
  public List<Goods> getAll() {
    return this.goodsService.getAll();
  }

  private interface GetAllJsonView extends Goods.BuyUserJsonView, Goods.CreateUserJsonView {
  }

  private interface GetByIdJsonView extends GetAllJsonView {
  }

  private interface GetByUserJsonView extends GetAllJsonView {
  }

}
