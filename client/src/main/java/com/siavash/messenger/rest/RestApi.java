package com.siavash.messenger.rest;

import com.siavash.messenger.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by sia on 6/25/16.
 */
public interface RestApi {
    @GET("user")
    Call<User> findUser(@Query("username") String userName);

    @GET("sign_in")
    Call<User> signIn(@Query("username") String userName, @Query("password") String password);

    @POST("sign_up")
    Call<Response> signUp(@Body User user);

    @POST("update_user")
    Call<Response> updateUser(@Body User user);

    @POST("delete_contact")
    Call<Response> deleteContact(@Body Contact contact);

    @GET("contacts")
    Call<List<Contact>> contacts(@Query("client_username") String clientUserName);

    @GET("find_contact")
    Call<List<Contact>> findContact(@Query("client_username") String clientUserName,
                                    @Query("name") String name);

    @POST("contact")
    Call<String> addContact(@Body Contact contact);

    @GET("msg")
    Call<List<Message>> message(@Query("client_username") String clientUserName,
                                @Query("contact_username") String contactUserName);

    @POST("msg")
    Call<Response> addMessage(@Body Message message);

    @POST("report_user")
    Call<Response> reportUser(@Body Request request);
}
