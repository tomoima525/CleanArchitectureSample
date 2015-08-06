package com.tomoima.cleanarchitecture.domain.repository;

import com.tomoima.cleanarchitecture.domain.model.Repos;

import java.util.Collection;

/**
 * Created by tomoaki on 8/7/15.
 */
public interface ReposRepository {

    void getRepos(String user, ReposListCallback cb );

    interface ReposListCallback{
        void onReposListLoaded(Collection<Repos> reposCollection);
        void onError();
    }
}
