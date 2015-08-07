package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.model.Repos;

import java.util.Collection;

/**
 * Created by tomoaki on 8/7/15.
 */
public interface ReposUseCase {

    interface ReposUserCaseCallback {
        void onReposListLoaded(Collection<Repos> reposCollection);
        void onError();
    }

    void execute(String user, ReposUserCaseCallback callback);

    void setCallback(ReposUserCaseCallback callback);
    void removeCallback();

}
