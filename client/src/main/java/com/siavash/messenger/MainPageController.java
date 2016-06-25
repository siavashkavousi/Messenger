package com.siavash.messenger;

import retrofit2.Call;

import java.io.IOException;

/**
 * Created by sia on 6/25/16.
 */
public class MainPageController {

    public static void main(String[] args) {
        RestApi restApi = Service.createService(RestApi.class, "localhost");
        Call<User> userRequest = restApi.user("siavash");
        try {
            User user = userRequest.execute().body();
            System.out.println(user.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
