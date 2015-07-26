package com.tomoima.cleanarchitecturesample.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tomoima.cleanarchitecturesample.R;
import com.tomoima.cleanarchitecturesample.models.User;
import com.tomoima.cleanarchitecturesample.models.apis.GithubApi;
import com.tomoima.cleanarchitecturesample.utils.StringUtil;
import com.tomoima.cleanarchitecturesample.views.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    @InjectView(R.id.search_result_view)
    ListView mListView;
    @InjectView(R.id.no_result_tv)
    TextView mNoResultTv;
    @InjectView(R.id.account_et)
    EditText mAccountEt;
    @InjectView(R.id.start_search_bt)
    Button searchBt;
    @InjectView(R.id.progress)
    View mProgress;

    private UserAdapter mUserAdapter;
    private GithubApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mUserAdapter = new UserAdapter(this, new ArrayList<User>());
        mListView.setAdapter(mUserAdapter);
        mAccountEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        mApi = createGithubApi();
    }

    @OnClick(R.id.start_search_bt)
    public void onClick(){
        searchBt.setFocusable(true);
        searchBt.setFocusableInTouchMode(true);
        searchBt.requestFocus();
        String text = mAccountEt.getText().toString();
        Timber.i(text);
        mListView.setVisibility(View.GONE);
        if(!StringUtil.isNullOrEmpty(text)) {
            mProgress.setVisibility(View.VISIBLE);
            new SearchTask().execute(text);
        } else {
            mNoResultTv.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
        }
    }

    private GithubApi createGithubApi() {

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(
                "https://api.github.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL);

//        final String githubToken = getResources().getString(R.string.github_oauth_token);
//        if (!StringUtil.isNullOrEmpty(githubToken)) {
//            builder.setRequestInterceptor(new RequestInterceptor() {
//                @Override
//                public void intercept(RequestFacade request) {
//                    request.addHeader("Authorization", format("token %s", githubToken));
//                }
//            });
//        }

        return builder.build().create(GithubApi.class);
    }

    private class SearchTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            mApi.listFollowersAsync(params[0], new Callback<List<User>>() {
                @Override
                public void success(List<User> userList, Response response) {
                    mProgress.setVisibility(View.GONE);
                    Timber.i("response ? = " + response);
                    if(userList.isEmpty()){
                        Timber.i( "no folllowers");
                        mNoResultTv.setVisibility(View.VISIBLE);
                        return;
                    } else{
                        mNoResultTv.setVisibility(View.GONE);
                        mListView.setVisibility(View.VISIBLE);
                    }
                    mUserAdapter.refresh(userList);
                }

                @Override
                public void failure(RetrofitError error) {
                    mProgress.setVisibility(View.GONE);
                    mNoResultTv.setVisibility(View.VISIBLE);
                }
            });
            return null;
        }
    }



}
