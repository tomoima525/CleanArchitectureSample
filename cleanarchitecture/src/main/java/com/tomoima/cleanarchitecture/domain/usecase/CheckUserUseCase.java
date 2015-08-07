package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.model.User;

/**
 * Created by tomoaki on 8/7/15.
 */
public interface CheckUserUseCase {

    interface CheckUserUseCaseCallback{
        void onChecked(User user);
        void onError();
    }

    void execute(User user, CheckUserUseCaseCallback callback);
}
