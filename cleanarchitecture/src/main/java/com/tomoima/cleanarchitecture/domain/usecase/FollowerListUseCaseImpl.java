package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.executor.PostExecutionThread;
import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.repository.UserRepository;
import com.tomoima.cleanarchitecture.utils.StringUtil;

import java.util.Collection;

/**
 * Created by tomoaki on 7/26/15.
 */
public class FollowerListUseCaseImpl extends UseCase<String> implements FollowerListUseCase, UserRepository.UserRepositoryCallback {
    private static FollowerListUseCaseImpl sUseCase;
    private final UserRepository mUserRepository;
    private PostExecutionThread mPostExecutionThread;
    private FollowerListUseCaseCallback mCallback;

    public static FollowerListUseCaseImpl getUseCase(UserRepository userRepository, PostExecutionThread postExecutionThread){
        if(sUseCase == null){
            sUseCase = new FollowerListUseCaseImpl(userRepository, postExecutionThread);
        }
        return sUseCase;
    }
    public FollowerListUseCaseImpl(UserRepository userRepository, PostExecutionThread postExecutionThread){
        mUserRepository = userRepository;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(String user, FollowerListUseCaseCallback callback) {
        mCallback = callback;
        this.start(user);
    }

    @Override
    protected void call(String user) {
        //validation
        if(validate(user)) {
            //access repository
            mUserRepository.getFollowers(user, this);
        }
    }

    public void setCallback(FollowerListUseCaseCallback callback) {
        mCallback = callback;
    }

    public void removeCallback(){
        mCallback = null;
    }

    private boolean validate(String user) {
        return !StringUtil.isNullOrEmpty(user);
    }

    @Override
    public void onUserListLoaded(final Collection<User> usersCollection) {
        //return to UIthread
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                if(mCallback != null) {
                    mCallback.onFollowerListLoaded(usersCollection);
                }
            }
        });
    }

    @Override
    public void onUserLoaded(User user) {

    }

    @Override
    public void onError() {
        //return to UIthread
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                if(mCallback != null) {
                    mCallback.onError();
                }
            }
        });
    }

}
