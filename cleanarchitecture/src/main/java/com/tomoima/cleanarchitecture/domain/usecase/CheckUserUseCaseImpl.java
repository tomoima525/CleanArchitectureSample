package com.tomoima.cleanarchitecture.domain.usecase;

import com.tomoima.cleanarchitecture.domain.executor.PostExecutionThread;
import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.repository.UserRepository;

import java.util.Collection;

/**
 * Created by tomoaki on 8/7/15.
 */
public class CheckUserUseCaseImpl extends UseCase<User> implements CheckUserUseCase, UserRepository.UserRepositoryCallback {

    private static CheckUserUseCaseImpl sUseCase;
    private final UserRepository mUserRepository;
    private PostExecutionThread mPostExecutionThread;
    private CheckUserUseCase.CheckUserUseCaseCallback mCallback;
    private User mUser;

    public static CheckUserUseCaseImpl getUseCase(UserRepository userRepository, PostExecutionThread postExecutionThread){
        if(sUseCase == null){
            sUseCase = new CheckUserUseCaseImpl(userRepository, postExecutionThread);
        }
        return sUseCase;
    }
    public CheckUserUseCaseImpl(UserRepository userRepository, PostExecutionThread postExecutionThread){
        mUserRepository = userRepository;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(User user, CheckUserUseCase.CheckUserUseCaseCallback callback){
        mCallback = callback;
        mUser = user;
        this.start(user);
    }

    @Override
    protected void call(User user) {
        mUserRepository.getUser(user.login,this);
    }

    @Override
    public void onUserListLoaded(Collection<User> usersCollection) {

    }

    @Override
    public void onUserLoaded(final User user) {

        final boolean isUserChecked = user != null;
        if(!isUserChecked) {
            //User is not cached in memory
            mUserRepository.putUser(mUser);
            return;
        }
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onChecked(user);
            }
        });
    }

    @Override
    public void onError() {

    }
}
