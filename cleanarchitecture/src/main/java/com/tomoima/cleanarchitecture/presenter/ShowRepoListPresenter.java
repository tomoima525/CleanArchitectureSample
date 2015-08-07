package com.tomoima.cleanarchitecture.presenter;

import com.tomoima.cleanarchitecture.domain.model.Repos;
import com.tomoima.cleanarchitecture.domain.usecase.ReposUseCase;

import java.util.Collection;

/**
 * Created by tomoaki on 8/7/15.
 */
public class ShowRepoListPresenter extends Presenter implements ReposUseCase.ReposUserCaseCallback{

    private ReposUseCase mReposUseCase;
    private ShowReposView mView;

    public ShowRepoListPresenter(ReposUseCase reposUseCase){
        mReposUseCase = reposUseCase;
    }

    public void setShowReposView(ShowReposView view){
        mView = view;
    }
    @Override
    public void initialize() {

    }

    @Override
    public void resume() {
        mReposUseCase.setCallback(this);
    }

    @Override
    public void pause() {
        mReposUseCase.removeCallback();
    }

    @Override
    public void destroy() {

    }

    public void getRepos(String user){
        mReposUseCase.execute(user, this);
    }

    @Override
    public void onReposListLoaded(Collection<Repos> reposCollection) {
        mView.showResult(reposCollection);
    }

    @Override
    public void onError() {

    }

    public interface ShowReposView {
        void showResult(Collection<Repos> reposes);
    }
}
