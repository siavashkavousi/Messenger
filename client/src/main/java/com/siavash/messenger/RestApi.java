package com.siavash.messenger;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by sia on 6/25/16.
 */
public interface RestApi {
    @GET("user")
    Call<User> user(@Query("username") String userName);

    @POST("user")
    Call<String> addUser(@Body User user);

    @GET("contact")
    Call<List<Contact>> contacts();

    @GET("contact")
    Call<Contact> contact(@Query("username") String userName);

    @POST("contact")
    Call<String> addContact(@Body Contact contact);

    @GET("msg")
    Call<Message> message(@Query("username") String userName);

    @POST("msg")
    Call<String> addMessage(@Body Message message);
}
