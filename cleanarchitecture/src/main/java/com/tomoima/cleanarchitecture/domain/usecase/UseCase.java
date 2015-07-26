package com.tomoima.cleanarchitecture.domain.usecase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tomoaki on 7/26/15.
 */
public abstract class UseCase<T> {
    // Everything inside this method will be executed asynchronously.
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public void start(final T params) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                call(params);
            }
        });
    }

    abstract protected void call(T params);
}
