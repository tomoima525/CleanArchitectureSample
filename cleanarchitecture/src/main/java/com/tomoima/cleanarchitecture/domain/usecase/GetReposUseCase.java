package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.model.Repos;

import java.util.Collection;

/**
 * Created by tomoaki on 8/7/15.
 */
public interface GetReposUseCase {
    void execute(String user, Callback callback);
    interface Callback {
        void onReposListLoaded(Collection<Repos> reposCollection);
        void onError();
    }

}
