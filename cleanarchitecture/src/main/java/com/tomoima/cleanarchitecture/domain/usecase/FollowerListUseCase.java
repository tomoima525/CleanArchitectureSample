package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.model.User;

import java.util.Collection;

/**
 * Created by tomoaki on 7/26/15.
 */
public interface FollowerListUseCase {
    /**
     * Callback used to be notified when a users collection has been loaded
     */
    interface FollowerListUseCaseCallback {
        void onFollowerListLoaded(Collection<User> usersCollection);
        void onError();
    }

    void execute(String user, FollowerListUseCaseCallback callback);

    void setCallback(FollowerListUseCaseCallback callback);
    void removeCallback();
}
