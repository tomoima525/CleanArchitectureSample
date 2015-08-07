package com.tomoima.cleanarchitecturesample.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tomoima.cleanarchitecturesample.R;
import com.tomoima.cleanarchitecturesample.consts.S;
import com.tomoima.cleanarchitecturesample.models.Repos;
import com.tomoima.cleanarchitecturesample.models.User;
import com.tomoima.cleanarchitecturesample.models.apis.GithubApi;
import com.tomoima.cleanarchitecturesample.views.adapter.RepoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tomoaki on 8/7/15.
 */
public class UserDetailActivity extends AppCompatActivity {

    @InjectView(R.id.detail_name_tv)
    TextView mDetailNameTv;
    @InjectView(R.id.detail_photo_riv)
    RoundedImageView mDetailPhotoIv;
    @InjectView(R.id.detail_repos_result_lv)
    ListView mDetailLv;

    private RepoAdapter mRepoAdapter;
    private GithubApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        User user = (User)getIntent().getSerializableExtra(S.user);
        ButterKnife.inject(this);
        Glide.with(this).load(user.avatar_url).into(mDetailPhotoIv);
        mDetailNameTv.setText(user.login);
        mRepoAdapter = new RepoAdapter(this, new ArrayList<Repos>());
        mDetailLv.setAdapter(mRepoAdapter);
        mApi = createGithubApi();
        new GetReposTask().execute(user.login);
    }

    private GithubApi createGithubApi() {

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(
                "https://api.github.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(GithubApi.class);
    }

    private class GetReposTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            mApi.listReposAsync(params[0], new Callback<List<Repos>>() {
                @Override
                public void success(List<Repos> reposList, Response response) {

                    if (reposList.isEmpty()) {

                        return;
                    }
                    mRepoAdapter.addAll(reposList);
                    mRepoAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
            return null;
        }
    }
}
