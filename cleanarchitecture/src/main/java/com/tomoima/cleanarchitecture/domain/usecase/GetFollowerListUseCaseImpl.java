package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.executor.PostExecutionThread;
import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.repository.UserRepository;
import com.tomoima.cleanarchitecture.utils.StringUtil;

import java.util.Collection;

/**
 * Created by tomoaki on 7/26/15.
 */
public class GetFollowerListUseCaseImpl extends UseCase<String> implements GetFollowerListUseCase, UserRepository.UserListCallback {
    private static GetFollowerListUseCaseImpl sUseCase;
    private final UserRepository mUserRepository;
    private PostExecutionThread mPostExecutionThread;
    private Callback mCallback;

    public static GetFollowerListUseCaseImpl getUseCase(UserRepository userRepository, PostExecutionThread postExecutionThread){
        if(sUseCase == null){
            sUseCase = new GetFollowerListUseCaseImpl(userRepository, postExecutionThread);
        }
        return sUseCase;
    }
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
        //validation
        if(validate(user)) {
            //access repository
            mUserRepository.getFollowers(user, this);
        }
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
                mCallback.onError();
            }
        });
    }

}
