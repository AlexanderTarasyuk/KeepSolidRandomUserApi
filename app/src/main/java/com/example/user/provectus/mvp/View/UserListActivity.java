package com.example.user.provectus.mvp.View;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.provectus.BuildConfig;
import com.example.user.provectus.datamanager.model.UserItem;
import com.example.user.provectus.R;

import com.example.user.provectus.di.DaggerUsersListPresenterImplComponent;
import com.example.user.provectus.mvp.View.Adapter.UserAdapter;
import com.example.user.provectus.mvp.View.Contract.UserListContract;
import com.example.user.provectus.manager.ConnectiviteManager;
import com.example.user.provectus.mvp.View.Contract.UserListPresenter;
import com.example.user.provectus.mvp.presenter.UsersListPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class UserListActivity extends AppCompatActivity implements UserListContract {

    @Inject
    UsersListPresenterImpl presenter;
    private UserAdapter adapter;
    private boolean isLoading = false;

    @BindView(R.id.swipe_layout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_list_users) RecyclerView recyclerUsers;
    @BindView(R.id.pb_load_list) ProgressBar progressBar;
    private Menu menu;

    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        gridLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager = new LinearLayoutManager(this);

        DaggerUsersListPresenterImplComponent.builder().build().inject(this);
        DaggerUsersListPresenterImplComponent.builder().build().inject(presenter);
        presenter.attach(this);
        if (presenter.isNetworkConnection(this)) {
            if (savedInstanceState == null) {
                renderContacts();
            }
        } else {
            setContentView(R.layout.no_connection_layout);
            Button buttonRestart = findViewById(R.id.btnNewConnection);
            buttonRestart.setOnClickListener(view -> {
                if (presenter.isNetworkConnection(this)) {
                    renderContacts();
                    setCorrectIcons();
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        adapter.notifyDataSetChanged();

    }

    private void renderContacts() {
        Timber.d("start rendering");
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
//        recyclerUsers = findViewById(R.id.rv_list_users);
//        progressBar = findViewById(R.id.pb_load_list);
//        refreshLayout = findViewById(R.id.swipe_layout);


        adapter = new UserAdapter(R.layout.item_load_more, this, new ArrayList<>());
        recyclerUsers.setLayoutManager(gridLayoutManager);
        recyclerUsers.setAdapter(adapter);
        setListeners();

        showLoading();

        presenter.loadData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_list_activity_menu, menu);
        this.menu = menu;
        setCorrectIcons();
        Timber.d("menu is created");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_list_style_action:
                toggleLayoutManager();
                setCorrectIcons();
                break;
        }
        return true;
    }

    @Override
    public void setList(List<UserItem> list) {
        isLoading = false;
        adapter.stopLoadMore();
        adapter.addList(list);
    }

    @Override
    public void hideLoading() {
        Timber.d("hide loading");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        Timber.d("show loading");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorLoading() {
        Toast.makeText(this, "Ошибка загрузки данных!", Toast.LENGTH_SHORT).show();
    }

    private void setCorrectIcons() {
        if (!getIsGridLayoutManager()) {
            menu.findItem(R.id.change_list_style_action).setIcon(R.drawable.view_sequential);
        } else {
            menu.findItem(R.id.change_list_style_action).setIcon(R.drawable.view_grid);
        }
    }

    private boolean getIsGridLayoutManager() {
        if (recyclerUsers == null) return true;
        return recyclerUsers.getLayoutManager() instanceof GridLayoutManager;
    }

    private void toggleLayoutManager() {
        if (recyclerUsers != null) {
            int visible_position = ((LinearLayoutManager) Objects.requireNonNull(recyclerUsers.getLayoutManager())).findFirstVisibleItemPosition();
            if (getIsGridLayoutManager()) {
                recyclerUsers.setLayoutManager(linearLayoutManager);
            } else {
                recyclerUsers.setLayoutManager(gridLayoutManager);
            }
            recyclerUsers.getLayoutManager().scrollToPosition(visible_position);
        }
    }

    private void setListeners() {
        recyclerUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) Objects
                        .requireNonNull(recyclerView.getLayoutManager()));
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItems) >= totalItemCount) {
                        if (presenter.isNetworkConnection((UserListActivity) getApplicationContext())) {
                            isLoading = true;
                            presenter.loadData();
                            adapter.startLoadMore();
                        }
                    }
                }
            }
        });

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isBottomProgressPosition(position) ? ((GridLayoutManager) Objects
                        .requireNonNull(recyclerUsers.getLayoutManager())).getSpanCount() : 1;
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            if (presenter.isNetworkConnection(this)) {
                adapter.clearData();
                presenter.loadData();
            } else {
                Toast.makeText(getApplicationContext(), "No network connection!", Toast.LENGTH_SHORT).show();
            }
            refreshLayout.setRefreshing(false);
        });
    }


}