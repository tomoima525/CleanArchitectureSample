package com.tomoima.cleanarchitecture.datasource.repository;

import com.tomoima.cleanarchitecture.datasource.api.ApiFactory;
import com.tomoima.cleanarchitecture.datasource.api.GithubApi;
import com.tomoima.cleanarchitecture.domain.model.Repos;
import com.tomoima.cleanarchitecture.domain.repository.ReposRepository;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tomoaki on 8/7/15.
 */
public class ReposRepositoryImpl implements ReposRepository {
    private static ReposRepositoryImpl sReposRepository;
    private GithubApi mApi;
    public ReposRepositoryImpl(){
        mApi = ApiFactory.createGithubApi();
    }
    public static ReposRepositoryImpl getRepository(){
        if(sReposRepository == null){
            sReposRepository = new ReposRepositoryImpl();
        }
        return sReposRepository;
    }
    @Override
    public void getRepos(String user, final ReposListCallback cb) {
        mApi.listReposAsync(user, new Callback<List<Repos>>() {
            @Override
            public void success(List<Repos> reposes, Response response) {
                cb.onReposListLoaded(reposes);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
