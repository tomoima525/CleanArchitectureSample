package com.tomoima.cleanarchitecture.domain.executor;

/**
 * Created by tomoaki on 7/26/15.
 */

public interface PostExecutionThread {
    //Thread abstraction created to change the execution context from any thread to any other thread.
    void post(Runnable runnable);
}
