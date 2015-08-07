package com.tomoima.cleanarchitecture.datasource.repository;

import com.tomoima.cleanarchitecture.datasource.api.ApiFactory;
import com.tomoima.cleanarchitecture.datasource.api.GithubApi;
import com.tomoima.cleanarchitecture.datasource.memory.UserMemoryCache;
import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.repository.UserRepository;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tomoaki on 7/26/15.
 */
public class UserRepositoryImpl implements UserRepository {
    private static UserRepositoryImpl sUserRepository;
    private GithubApi mApi;

    public UserRepositoryImpl(){
        mApi = ApiFactory.createGithubApi();
    }
    public static UserRepositoryImpl getRepository(){
        if(sUserRepository == null) {
            sUserRepository = new UserRepositoryImpl();
        }
        return sUserRepository;
    }

    @Override
    public void getFollowers(String userId, final UserRepositoryCallback userRepositoryCallback) {
        mApi.listFollowersAsync(userId, new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                userRepositoryCallback.onUserListLoaded(users);
            }

            @Override
            public void failure(RetrofitError error) {
                userRepositoryCallback.onError();
            }
        });
    }

    @Override
    public void getUser(String userId, UserRepositoryCallback userRepositoryCallback) {

        User user = UserMemoryCache.getInstance().getUser(userId);
        userRepositoryCallback.onUserLoaded(user);
    }

    @Override
    public void putUser(User user) {
        UserMemoryCache.getInstance().put(user.login, user);
    }


}
