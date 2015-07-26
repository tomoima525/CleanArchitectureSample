package com.tomoima.simplearchitecture.domain.executor;

import java.util.concurrent.Executor;

/**
 * Created by tomoaki on 7/26/15.
 */

// ThreadExecutor is used to execute UseCases out of UIThread
public interface ThreadExecutor extends Executor {
    void execute(final Runnable runnable);
}
