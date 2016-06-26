package com.siavash.messenger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sia on 6/25/16.
 */
public class MainPageController {

    public static void main(String[] args) throws IOException {
        RestApi restApi = Service.createService(RestApi.class, "http://127.0.0.1:8000/");
//        Call<User> userRequest = restApi.user("siavash");
//
//        User user = null;
//        try {
//            user = userRequest.execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(user.toString());

        List<String> list = new ArrayList<>();
        list.add("daddy");
        Call<ResponseBody> sendMessageRequest = restApi.sendMessage(
                new Message("siavash", "salam khoobi", list));

        try {
            sendMessageRequest.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Call<String> sendUser = restApi.sendUser(new User("siavash", "siavash", "kav", "9123"));
//        sendUser.execute().body();
    }
}
