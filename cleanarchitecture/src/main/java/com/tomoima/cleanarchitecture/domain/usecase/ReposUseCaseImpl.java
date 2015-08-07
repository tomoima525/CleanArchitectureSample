package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.executor.PostExecutionThread;
import com.tomoima.cleanarchitecture.domain.model.Repos;
import com.tomoima.cleanarchitecture.domain.repository.ReposRepository;

import java.util.Collection;

/**
 * Created by tomoaki on 8/7/15.
 */
public class ReposUseCaseImpl extends UseCase<String> implements ReposUseCase, ReposRepository.ReposListCallback{
    private static ReposUseCaseImpl sReposUseCaseImpl;
    private ReposRepository mReposRepository;
    private PostExecutionThread mThread;
    private ReposUseCase.ReposUserCaseCallback mCallback;

    public ReposUseCaseImpl(ReposRepository reposRepository, PostExecutionThread thread){
        mReposRepository = reposRepository;
        mThread = thread;
    }

    public static ReposUseCaseImpl getUseCase(ReposRepository reposRepository, PostExecutionThread thread){
        if(sReposUseCaseImpl == null){
            sReposUseCaseImpl = new ReposUseCaseImpl(reposRepository, thread);
        }
        return sReposUseCaseImpl;
    }


    @Override
    public void execute(String user, ReposUserCaseCallback callback) {
        mCallback = callback;
        this.start(user);
    }

    @Override
    protected void call(String params) {
        mReposRepository.getRepos(params,this);
    }

    public void setCallback(ReposUserCaseCallback callback) {
        mCallback = callback;
    }

    public void removeCallback(){
        mCallback = null;
    }

    @Override
    public void onReposListLoaded(final Collection<Repos> reposCollection) {
        mThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onReposListLoaded(reposCollection);
            }
        });

    }

    @Override
    public void onError() {

    }
}
