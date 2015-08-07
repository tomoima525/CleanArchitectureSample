package com.tomoima.cleanarchitecture.domain.repository;

import com.tomoima.cleanarchitecture.domain.model.User;

import java.util.Collection;

/**
 * Created by tomoaki on 7/26/15.
 */
public interface UserRepository {
    // CRUD interface
    void getFollowers(String userId, UserRepositoryCallback userRepositoryCallback);
    void getUser(String userId, UserRepositoryCallback userRepositoryCallback);
    void putUser(User user);
    interface UserRepositoryCallback {
        void onUserListLoaded(Collection<User> usersCollection);
        void onUserLoaded(User user);
        void onError();
    }
}
