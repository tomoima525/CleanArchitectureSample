package com.tomoima.cleanarchitecturesample.models.apis;


import com.tomoima.cleanarchitecturesample.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GithubApi {
    @GET("/users/{user}/followers")
    List<User> listFollowers(@Path("user") String user);

    @GET("/users/{user}/followers")
    void listFollowersAsync(@Path("user") String user, Callback<List<User>> cb);
}
