package com.mohit.diagnallistingpage.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mohit.diagnallistingpage.R;
import com.mohit.diagnallistingpage.adapter.PageListAdapter;
import com.mohit.diagnallistingpage.data.remote.response.page.ContentItem;
import com.mohit.diagnallistingpage.data.remote.response.page.PageListResponse;
import com.mohit.diagnallistingpage.viewmodel.ListingPageViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The listing page activity which will list the popular listing.
 * <p>
 * Author: Mohit issar
 * Email: 007mohitissar@gmail.com
 * Created: 05/01/2020
 * Modified: 05/01/2020
 */
public class ListingPageActivity extends AppCompatActivity implements PageListAdapter.PageListAdapterListener {

    @BindView(R.id.listing_rv)
    RecyclerView listingRv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shadow_ll)
    LinearLayout shadowll;
    @BindView(R.id.progressBar)
    AVLoadingIndicatorView progressBar;
    PageListAdapter pageListAdapter;
    boolean isLoading = false;
    int total_page = 3;
    int current_page = 1;
    private ListingPageViewModel listingPageViewModel;
    private List<ContentItem> contentItemArrayList = new ArrayList<>();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_page);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        listingPageViewModel = ViewModelProviders.of(this).get(ListingPageViewModel.class);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        //setVisibility(true, isLoading);
        setAdapter();
        setObserve();
        initScrollListener();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            listingRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 7));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            listingRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        }
    }

    private void setAdapter() {
        setPageListAdapter();
    }

    private void setPageListAdapter() {
        RecyclerView.ItemAnimator animator = listingRv.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        pageListAdapter = new PageListAdapter(contentItemArrayList, this, getApplicationContext());
        listingRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        listingRv.setAdapter(pageListAdapter);
    }

    private void initScrollListener() {
        listingRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == contentItemArrayList.size() - 1 && current_page <= total_page) {
                        //bottom of list!
                        isLoading = true;
                        progressBar.show();
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                current_page += 1;
                listingPageViewModel.prepareListData(current_page);
                isLoading = false;
                progressBar.hide();
            }
        }, 2000);
    }

    /**
     * Converting dp to pixel
     */
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.findViewById(androidx.appcompat.R.id.search_src_text).setBackgroundResource(R.drawable.abc_textfield_search_default_mtrl_alpha);

        //changing edittext color
        EditText searchEdit = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchEdit.setTextAppearance(R.style.EditTextStyle);
        }
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                pageListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                pageListAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void setObserve() {
        listingPageViewModel.prepareListData(current_page);
        listingPageViewModel.getPageListLD().observe(this, new Observer<PageListResponse>() {
            @Override
            public void onChanged(PageListResponse pageListResponse) {
                if (pageListResponse != null) {
                    isLoading = false;
                    progressBar.hide();
                    toolbar.setTitle(pageListResponse.getPage().getTitle());
                    contentItemArrayList.addAll(pageListResponse.getPage().getContentItems().getContent());
                    pageListAdapter.refreshAdapter(contentItemArrayList);
                }
            }
        });
    }

    @Override
    public void onContentSelected(ContentItem contentItem) {
    }
}
