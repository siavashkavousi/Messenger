package com.siavash.messenger;

import retrofit2.Call;

import java.io.IOException;

/**
 * Created by sia on 6/25/16.
 */
public class MainPageController {

    public static void main(String[] args) {
        RestApi restApi = Service.createService(RestApi.class, "http://127.0.0.1:8000/");
        Call<User> userRequest = restApi.user("siavash");

        User user = null;
        try {
            user = userRequest.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(user.toString());
    }
}
