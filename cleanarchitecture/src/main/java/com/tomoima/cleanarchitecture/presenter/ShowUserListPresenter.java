package com.tomoima.cleanarchitecture.presenter;

import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.usecase.GetFollowerListUseCase;

import java.util.Collection;

/**
 * Created by tomoaki on 7/26/15.
 */
public class ShowUserListPresenter extends Presenter implements GetFollowerListUseCase.Callback {

    private GetFollowerListUseCase mGetFollowerListUseCase;
    private ShowUserListView mShowUserListView;

    public ShowUserListPresenter(GetFollowerListUseCase getFollowerListUseCase){
        mGetFollowerListUseCase = getFollowerListUseCase;
    }

    public void setShowUserListView(ShowUserListView view){
        mShowUserListView = view;
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

    public void getFollowerList(String user){
        mShowUserListView.showLoading();
        mGetFollowerListUseCase.execute(user, this);
    }

    @Override
    public void onFollowerListLoaded(Collection<User> usersCollection) {
        mShowUserListView.hideLoading();
        mShowUserListView.hideNoResultCase();
        mShowUserListView.showResult(usersCollection);

    }

    @Override
    public void onError() {
        mShowUserListView.hideLoading();
        mShowUserListView.showNoResultCase();
    }


    public interface ShowUserListView {
        void showLoading();
        void hideLoading();
        void showNoResultCase();
        void hideNoResultCase();
        void showResult(Collection<User> usersCollection);

    }
}
