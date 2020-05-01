package com.mohit.diagnallistingpage.data.remote.response.page;

import com.google.gson.annotations.SerializedName;

/**
 * The model class which holds the single content data
 * <p>
 * Author: Mohit issar
 * Email: 007mohitissar@gmail.com
 * Created: 05/01/2020
 * Modified: 05/01/2020
 */
public class ContentItem{

    @SerializedName("name")
    private String name;

    @SerializedName("poster-image")
    private String posterImage;

    /**
     * The Method set the content name
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * This method get the name
     *
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * The method set the poster image
     *
     * @param posterImage
     */
    public void setPosterImage(String posterImage){
        this.posterImage = posterImage;
    }

    /**
     * This method get the poster Image
     *
     * @return
     */
    public String getPosterImage(){
        return posterImage;
    }
}