package com.mohit.diagnallistingpage.data.remote.response.page;

import com.google.gson.annotations.SerializedName;

/**
 * The model class which holds the Page data
 * <p>
 * Author: Mohit issar
 * Email: 007mohitissar@gmail.com
 * Created: 05/01/2020
 * Modified: 05/01/2020
 */
public class PageListResponse {

    @SerializedName("page")
    private Page page;

    /**
     * This method sets the page entities
     * @param page - entities
     */
    public void setPage(Page page){
        this.page = page;
    }

    /**
     * This method return the of Page entities
     * @return List of entities
     */
    public Page getPage(){
        return page;
    }
}