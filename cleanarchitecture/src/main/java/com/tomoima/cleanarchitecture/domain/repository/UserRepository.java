package com.tomoima.cleanarchitecture.domain.repository;

import com.tomoima.cleanarchitecture.domain.model.User;

import java.util.Collection;

/**
 * Created by tomoaki on 7/26/15.
 */
public interface UserRepository {
    // CRUD interface
    void getFollowers(String user, UserListCallback userListCallback);


    interface UserListCallback {
        void onUserListLoaded(Collection<User> usersCollection);
        void onError();
    }
}
