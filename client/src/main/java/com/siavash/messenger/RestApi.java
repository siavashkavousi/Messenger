package com.siavash.messenger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sia on 6/25/16.
 */
public interface RestApi {
    @GET("user")
    Call<User> user(@Query("username") String userName);

    @POST("user")
    Call<String> sendUser(@Body User user);

    @POST("msg")
    Call<ResponseBody> sendMessage(@Body Message message);
}
