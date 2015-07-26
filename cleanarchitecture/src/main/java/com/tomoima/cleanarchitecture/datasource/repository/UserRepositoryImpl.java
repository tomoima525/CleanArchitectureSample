package com.tomoima.cleanarchitecture.datasource.repository;

import android.util.Log;

import com.tomoima.cleanarchitecture.datasource.api.GithubApi;
import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.repository.UserRepository;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tomoaki on 7/26/15.
 */
public class UserRepositoryImpl implements UserRepository {
    private GithubApi mApi;

    public UserRepositoryImpl(){
        mApi = createGithubApi();
    }

    private GithubApi createGithubApi() {

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(
                "https://api.github.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL);

//        final String githubToken = getResources().getString(R.string.github_oauth_token);
//        if (!StringUtil.isNullOrEmpty(githubToken)) {
//            builder.setRequestInterceptor(new RequestInterceptor() {
//                @Override
//                public void intercept(RequestFacade request) {
//                    request.addHeader("Authorization", format("token %s", githubToken));
//                }
//            });
//        }

        return builder.build().create(GithubApi.class);
    }
    @Override
    public void getFollowers(String user, final UserListCallback userListCallback) {
        mApi.listFollowersAsync(user, new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                userListCallback.onUserListLoaded(users);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("UserRepositoryIml", "onError!!!");
                userListCallback.onError();
            }
        });
    }


}
