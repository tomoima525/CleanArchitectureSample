package com.tomoima.cleanarchitecture.datasource.api;

import retrofit.RestAdapter;

/**
 * Created by tomoaki on 8/7/15.
 */
public class ApiFactory {
    public static GithubApi createGithubApi(){
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(
                "https://api.github.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(GithubApi.class);
    }
}
