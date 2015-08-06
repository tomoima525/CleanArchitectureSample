package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.executor.PostExecutionThread;
import com.tomoima.cleanarchitecture.domain.model.Repos;
import com.tomoima.cleanarchitecture.domain.repository.ReposRepository;

import java.util.Collection;

/**
 * Created by tomoaki on 8/7/15.
 */
public class GetReposUseCaseImpl extends UseCase<String> implements GetReposUseCase, ReposRepository.ReposListCallback{
    private static GetReposUseCaseImpl sGetReposUseCaseImpl;
    private ReposRepository mReposRepository;
    private PostExecutionThread mThread;
    private GetReposUseCase.Callback mCallback;

    public GetReposUseCaseImpl(ReposRepository reposRepository, PostExecutionThread thread){
        mReposRepository = reposRepository;
        mThread = thread;
    }

    public static GetReposUseCaseImpl getUseCase(ReposRepository reposRepository, PostExecutionThread thread){
        if(sGetReposUseCaseImpl == null){
            sGetReposUseCaseImpl = new GetReposUseCaseImpl(reposRepository, thread);
        }
        return  sGetReposUseCaseImpl;
    }


    @Override
    public void execute(String user, Callback callback) {
        mCallback = callback;
        this.start(user);
    }

    @Override
    protected void call(String params) {
        mReposRepository.getRepos(params,this);
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
