package com.xiaoqiangzzz.deal_hebut.service;
import com.xiaoqiangzzz.deal_hebut.entity.OnPassword;
import com.xiaoqiangzzz.deal_hebut.entity.User;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import okhttp3.RequestBody;

public class UserService {
    private static final String LOCAL_URL = "api/";
    public static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public static final String TOKEN_HEADER = "Authorization";

    public BehaviorSubject<User> currentUser = BehaviorSubject.createDefault(new User());  // 当前登陆用户

    BaseHttpService httpService = BaseHttpService.getInstance();

    /**
     * 登陆
     *
     * @param callBack
     * @param user
     */
    public void login(BaseHttpService.CallBack callBack, User user) {
        httpService.post(LOCAL_URL + "user/login", user, callBack, User.class);
    }

    /**
     * 获取当前登陆用户
     *
     * @param callBack
     */
    public void getCurrentUser(BaseHttpService.CallBack callBack) {
        httpService.get(LOCAL_URL + "user/currentLoginUser", callBack, User.class);
    }

    /**
     * 修改头像
     *
     * @param data
     * @param callBack
     */
    public void uploadImage(RequestBody data, BaseHttpService.CallBack callBack) {
        httpService.putByForm(LOCAL_URL + "user/changeImage", data, callBack, String.class);
    }

    public void updateUsername(BaseHttpService.CallBack callBack, User user) {
        httpService.post(LOCAL_URL + "user/updateUsername", user, callBack, User.class);
    }

    public void updatePassword(BaseHttpService.CallBack callBack, OnPassword user) {
        httpService.post(LOCAL_URL + "user/updatePassword", user, callBack, User.class);
    }
}
