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

    @GET("sign_in")
    Call<User> signIn(@Query("username") String userName, @Query("password") String password);

    @POST("user")
    Call<String> addUser(@Body User user);

    @GET("contact")
    Call<List<Contact>> contacts(@Query("client_username") String clientUserName);

    @POST("contact")
    Call<String> addContact(@Body Contact contact);

    @GET("msg")
    Call<List<Message>> message(@Query("client_username") String clientUserName,
                                @Query("contact_username") String contactUserName);

    @POST("msg")
    Call<String> addMessage(@Body Message message);
}
