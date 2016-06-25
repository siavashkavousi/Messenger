package com.siavash.messenger;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by sia on 6/25/16.
 */
public interface RestApi {
    @GET("user")
    Call<User> user(@Query("username") String userName);
}
