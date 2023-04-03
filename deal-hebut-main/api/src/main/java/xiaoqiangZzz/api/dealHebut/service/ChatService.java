package xiaoqiangZzz.api.dealHebut.service;

import xiaoqiangZzz.api.dealHebut.entity.Chat;

import java.util.List;

public interface ChatService {

  Chat getBySellerIdAndCurrentUser(Long id);

  List<Chat> getAllByCurrentUser();
}
