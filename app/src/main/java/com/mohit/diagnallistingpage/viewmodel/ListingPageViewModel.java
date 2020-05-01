package com.mohit.diagnallistingpage.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohit.diagnallistingpage.data.remote.repository.ListingPageRepository;
import com.mohit.diagnallistingpage.data.remote.response.page.PageListResponse;

/**
 * Listing Page view model
 * <p>
 * Author: Mohit issar
 * Email: 007mohitissar@gmail.com
 * Created: 05/01/2020
 * Modified: 05/01/2020
 */
public class ListingPageViewModel extends AndroidViewModel {
    Context context;
    ListingPageRepository listingPageRepository;
    private LiveData<PageListResponse> pageListLD;

    public ListingPageViewModel(@NonNull Application application) {
        super(application);
        listingPageRepository = ListingPageRepository.getInstance(application);
        pageListLD = listingPageRepository.getPageListMLD();
        context = application.getApplicationContext();
    }

    /**
     * This methos return page list live data
     *
     * @return
     */
    public LiveData<PageListResponse> getPageListLD() {
        return pageListLD;
    }

    /**
     * This method used to load the page list data
     *
     * @param page pass the page count
     */
    public void prepareListData(int page) {
        listingPageRepository.prepareListData(page);
    }
}
