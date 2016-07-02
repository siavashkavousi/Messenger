package com.siavash.messenger.rest;

import com.siavash.messenger.*;
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

    @GET("find_contacts")
    Call<List<Contact>> findContacts(@Query("client_username") String clientUserName,
                                     @Query("name") String name);

    @GET("find_contact")
    Call<Contact> findContact(@Query("client_username") String clientUserName,
                                     @Query("contact_username") String contactUserName);

    @POST("contact")
    Call<String> addContact(@Body Contact contact);

    @GET("msg")
    Call<List<Message>> message(@Query("client_username") String clientUserName,
                                @Query("contact_username") String contactUserName);

    @POST("msg")
    Call<Response> addMessage(@Body Message message);

    @POST("report_user")
    Call<Response> reportUser(@Body Request reportedUserName);

    /************* groups *************/

    @POST("create_group")
    Call<Response> createGroup(@Body Group group);

    @GET("groups")
    Call<List<Group>> groups(@Query("client_username") String clientUserName);

    @GET("group_members")
    Call<GroupMembers> groupMembers(@Query("group_id") String groupId);

    @POST("group_members")
    Call<Response> addGroupMembers(@Body GroupMembers groupMembers);

    @POST("group_message")
    Call<Response> addGroupMessage(@Body Message message);

    @GET("group_messages")
    Call<List<Message>> groupMessages(@Query("client_username") String clientUserName,
                                      @Query("group_id") String groupId,
                                      @Query("except_client") boolean exceptClient);
}
