package com.tomoima.simplearchitecture.domain.repository;

import com.tomoima.simplearchitecture.domain.model.User;

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
