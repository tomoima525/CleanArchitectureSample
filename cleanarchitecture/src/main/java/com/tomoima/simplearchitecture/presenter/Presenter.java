package com.tomoima.simplearchitecture.presenter;

/**
 * Created by tomoaki on 7/26/15.
 */
public abstract class Presenter {
    /**
     * Called when the presenter is initialized, this method represents the start of the presenter
     * lifecycle.
     */
    public abstract void initialize();

    /**
     * Called when the presenter is resumed. After the initialization and when the presenter comes
     * from a pause state.
     */
    public abstract void resume();

    /**
     * Called when the presenter is paused.
     */
    public abstract void pause();

    /**
     * Called when the presenter is destroied.
     */
    public abstract void destroy();

}
