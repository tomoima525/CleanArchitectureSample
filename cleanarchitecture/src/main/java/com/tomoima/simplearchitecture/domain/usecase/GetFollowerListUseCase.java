package com.tomoima.simplearchitecture.domain.usecase;

import com.tomoima.simplearchitecture.domain.model.User;

import java.util.Collection;

/**
 * Created by tomoaki on 7/26/15.
 */
public interface GetFollowerListUseCase {
    /**
     * Callback used to be notified when a users collection has been loaded
     */
    interface Callback {
        void onFollowerListLoaded(Collection<User> usersCollection);
        void onError();
    }

    void execute(String user, Callback callback);
}
