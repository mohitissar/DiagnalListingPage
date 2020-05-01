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
public class Page{

    @SerializedName("page-num")
    private String pageNum;

    @SerializedName("page-size")
    private String pageSize;

    @SerializedName("content-items")
    private ContentItems contentItems;

    @SerializedName("total-content-items")
    private String totalContentItems;

    @SerializedName("title")
    private String title;

    /**
     * This method set page number
     *
     * @param pageNum
     */
    public void setPageNum(String pageNum){
        this.pageNum = pageNum;
    }

    /**
     * This method get the page number
     *
     * @return
     */
    public String getPageNum(){
        return pageNum;
    }

    /**
     * This method set the page size
     *
     * @param pageSize
     */
    public void setPageSize(String pageSize){
        this.pageSize = pageSize;
    }

    /**
     * This method get page size
     *
     * @return
     */
    public String getPageSize(){
        return pageSize;
    }

    /**
     * This method set th content items
     *
     * @param contentItems
     */
    public void setContentItems(ContentItems contentItems){
        this.contentItems = contentItems;
    }

    /**
     * This get The content Items
     *
     * @return
     */
    public ContentItems getContentItems(){
        return contentItems;
    }

    /**
     * This method set The Total count of content Items
     *
     * @param totalContentItems
     */
    public void setTotalContentItems(String totalContentItems){
        this.totalContentItems = totalContentItems;
    }

    /**
     * This method get The Total count of content Items
     *
     * @return
     */
    public String getTotalContentItems(){
        return totalContentItems;
    }

    /**
     * This method set Title of page
     *
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * This method get the Title of page
     *
     * @return
     */
    public String getTitle(){
        return title;
    }
}