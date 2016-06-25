package com.siavash.messenger;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sia on 6/25/16.
 */
public interface RestApi {
    @GET("user")
    Call<User> user(@Field("username") String userName);

//    @POST("user/register")
//    Call<> sendUser(@Body User user);
}
