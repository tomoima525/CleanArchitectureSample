package com.tomoima.cleanarchitecture.presenter;

import com.tomoima.cleanarchitecture.domain.model.Repos;
import com.tomoima.cleanarchitecture.domain.usecase.GetReposUseCase;

import java.util.Collection;

/**
 * Created by tomoaki on 8/7/15.
 */
public class ShowRepoListPresenter extends Presenter implements GetReposUseCase.Callback{

    private GetReposUseCase mGetReposUseCase;
    private ShowReposView mView;

    public ShowRepoListPresenter(GetReposUseCase getReposUseCase){
        mGetReposUseCase = getReposUseCase;
    }

    public void setShowReposView(ShowReposView view){
        mView = view;
    }
    @Override
    public void initialize() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void getRepos(String user){
        mGetReposUseCase.execute(user, this);
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
