package com.tomoima.cleanarchitecture.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tomoima.cleanarchitecture.R;
import com.tomoima.cleanarchitecture.consts.S;
import com.tomoima.cleanarchitecture.datasource.api.GithubApi;
import com.tomoima.cleanarchitecture.datasource.repository.ReposRepositoryImpl;
import com.tomoima.cleanarchitecture.domain.executor.UIThread;
import com.tomoima.cleanarchitecture.domain.model.Repos;
import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.repository.ReposRepository;
import com.tomoima.cleanarchitecture.domain.usecase.ReposUseCase;
import com.tomoima.cleanarchitecture.domain.usecase.ReposUseCaseImpl;
import com.tomoima.cleanarchitecture.presenter.ShowRepoListPresenter;
import com.tomoima.cleanarchitecture.ui.adapter.RepoAdapter;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tomoaki on 8/7/15.
 */
public class UserDetailActivity extends AppCompatActivity implements ShowRepoListPresenter.ShowReposView{

    @InjectView(R.id.detail_name_tv)
    TextView mDetailNameTv;
    @InjectView(R.id.detail_photo_riv)
    RoundedImageView mDetailPhotoIv;
    @InjectView(R.id.detail_repos_result_lv)
    ListView mDetailLv;

    private ShowRepoListPresenter mShowReposListPresenter;
    private RepoAdapter mRepoAdapter;
    private GithubApi mApi;
    private User mUser;

    public static Intent createIntent(Context context, User user){
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra(S.user, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        mUser = (User)getIntent().getSerializableExtra(S.user);
        ButterKnife.inject(this);
        Glide.with(this).load(mUser.avatar_url).into(mDetailPhotoIv);
        mDetailNameTv.setText(mUser.login);
        mRepoAdapter = new RepoAdapter(this, new ArrayList<Repos>());
        mDetailLv.setAdapter(mRepoAdapter);
        initialize();
    }

    private void initialize(){

        //DataSource Layer: RepositoryImpl
        ReposRepository reposRepositoryImpl = ReposRepositoryImpl.getRepository();

        //Domain Layer: UseCase
        ReposUseCase getReposUseCaseImpl = ReposUseCaseImpl.getUseCase(reposRepositoryImpl, UIThread.getInstance());

        mShowReposListPresenter = new ShowRepoListPresenter(getReposUseCaseImpl);
        mShowReposListPresenter.setShowReposView(this);
        mShowReposListPresenter.getRepos(mUser.login);
    }

    @Override
    public void showResult(Collection<Repos> reposes) {
        mRepoAdapter.addAll(reposes);
        mRepoAdapter.notifyDataSetChanged();
    }
}
