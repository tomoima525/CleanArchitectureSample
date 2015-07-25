package com.tomoima.cleanarchitecturesample.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;

import com.tomoima.cleanarchitecturesample.MyApplication;


public class BaseFragment extends Fragment {

    public Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    protected void finish() {
        if (isResumed())
            getActivity().finish();
    }

    /**
     * {@link Fragment#getResources()} sometimes fails because a fragment is not attached to an activity<br>
     * Use this method to call getResources from Application<br>
     *
     * @return Resources
     */
    public Resources getResourcesFromApplication() {
        return MyApplication.getInstance().getResources();
    }

}
