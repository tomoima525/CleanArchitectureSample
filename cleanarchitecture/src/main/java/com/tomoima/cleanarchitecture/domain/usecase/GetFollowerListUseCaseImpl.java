package com.tomoima.cleanarchitecture.domain.usecase;

import android.util.Log;

import com.tomoima.cleanarchitecture.domain.executor.PostExecutionThread;
import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.repository.UserRepository;

import java.util.Collection;

/**
 * Created by tomoaki on 7/26/15.
 */
public class GetFollowerListUseCaseImpl extends UseCase<String> implements GetFollowerListUseCase, UserRepository.UserListCallback {
    private final UserRepository mUserRepository;
    private PostExecutionThread mPostExecutionThread;
    private Callback mCallback;

    public GetFollowerListUseCaseImpl(UserRepository userRepository, PostExecutionThread postExecutionThread){
        mUserRepository = userRepository;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(String user, Callback callback) {
        mCallback = callback;
        this.start(user);
    }

    @Override
    protected void call(String user) {
        mUserRepository.getFollowers(user, this);
    }

    @Override
    public void onUserListLoaded(final Collection<User> usersCollection) {
        //return to UIthread
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFollowerListLoaded(usersCollection);
            }
        });
    }

    @Override
    public void onError() {
        //return to UIthread
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                Log.d("GetFollowerListUseCase", "onError!!!");
                mCallback.onError();
            }
        });
    }

}