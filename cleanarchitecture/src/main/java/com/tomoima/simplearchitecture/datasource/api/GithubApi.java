package com.tomoima.simplearchitecture.datasource.api;

import com.tomoima.simplearchitecture.domain.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by tomoaki on 7/26/15.
 */
public interface GithubApi {
    @GET("/users/{user}/followers")
    List<User> listFollowers(@Path("user") String user);

    @GET("/users/{user}/followers")
    void listFollowersAsync(@Path("user") String user, Callback<List<User>> cb);
}
