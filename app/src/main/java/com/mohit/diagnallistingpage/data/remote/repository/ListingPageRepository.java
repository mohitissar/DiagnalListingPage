package com.mohit.diagnallistingpage.data.remote.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.mohit.diagnallistingpage.data.remote.response.page.PageListResponse;
import com.mohit.diagnallistingpage.util.Utils;

/**
 * The Listing Page repository which has access to remote data fetching services
 * <p>
 * Author: Mohit issar
 * Email: 007mohitissar@gmail.com
 * Created: 05/01/2020
 * Modified: 05/01/2020
 */
public class ListingPageRepository {
    private static volatile ListingPageRepository mInstance;
    private final MutableLiveData<PageListResponse> pageListMLD = new MutableLiveData<>();
    Application application;

    private ListingPageRepository(Application application) {
        this.application = application;
    }

    public static ListingPageRepository getInstance(Application application) {
        if (mInstance == null) {
            synchronized (ListingPageRepository.class) {
                if (mInstance == null) {
                    mInstance = new ListingPageRepository(application);
                }
            }
        }
        return mInstance;
    }

    /**
     * This methos return page list live data
     *
     * @return
     */
    public MutableLiveData<PageListResponse> getPageListMLD() {
        return pageListMLD;
    }

    /**
     * This method used to load the page list data
     *
     * @param page pass the page count
     */
    public void prepareListData(int page) {
        PageListResponse pageListResponse = Utils.loadInfiniteFeeds(application.getApplicationContext(), page);
        pageListMLD.postValue(pageListResponse);
    }
}
