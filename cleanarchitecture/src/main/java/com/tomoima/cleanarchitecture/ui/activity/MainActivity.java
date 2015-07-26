package com.tomoima.cleanarchitecture.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tomoima.cleanarchitecture.R;
import com.tomoima.cleanarchitecture.datasource.repository.UserRepositoryImpl;
import com.tomoima.cleanarchitecture.domain.executor.UIThread;
import com.tomoima.cleanarchitecture.domain.model.User;
import com.tomoima.cleanarchitecture.domain.repository.UserRepository;
import com.tomoima.cleanarchitecture.domain.usecase.GetFollowerListUseCase;
import com.tomoima.cleanarchitecture.domain.usecase.GetFollowerListUseCaseImpl;
import com.tomoima.cleanarchitecture.presenter.ShowUserListPresenter;
import com.tomoima.cleanarchitecture.ui.adapter.UserAdapter;
import com.tomoima.cleanarchitecture.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ShowUserListPresenter.ShowUserListView{

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

    //Presentation layer: Presenter
    ShowUserListPresenter mShowUserListPresenter;

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

        initialize();

    }

    private void initialize(){

        //DataSource Layer: RepositoryImpl
        UserRepository userRepositoryImpl = new UserRepositoryImpl();

        //Domain Layer: UseCase
        GetFollowerListUseCase getFollowerListUserCaseImpl = new GetFollowerListUseCaseImpl(userRepositoryImpl, UIThread.getInstance());

        mShowUserListPresenter = new ShowUserListPresenter(getFollowerListUserCaseImpl);
        mShowUserListPresenter.setShowUserListView(this);
    }

    @OnClick(R.id.start_search_bt)
    public void onClick(){
        searchBt.setFocusable(true);
        searchBt.setFocusableInTouchMode(true);
        searchBt.requestFocus();
        String text = mAccountEt.getText().toString();
        if(!StringUtil.isNullOrEmpty(text)) {
            mShowUserListPresenter.getFollowerList(text);
        }
    }

    @Override
    public void showLoading() {
        mListView.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showNoResultCase() {
        mListView.setVisibility(View.GONE);
        mNoResultTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResultCase() {
        mNoResultTv.setVisibility(View.GONE);
    }

    @Override
    public void showResult(Collection<User> usersCollection) {
        mListView.setVisibility(View.VISIBLE);
        mUserAdapter.refresh(usersCollection);
    }
}
