package com.mohit.diagnallistingpage.data.remote.response.page;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * The model class which holds the content List data
 * <p>
 * Author: Mohit issar
 * Email: 007mohitissar@gmail.com
 * Created: 05/01/2020
 * Modified: 05/01/2020
 */
public class ContentItems{

    @SerializedName("content")
    private List<ContentItem> content;

    /**
     * This method sets the Content list entities
     * @param content - entities
     */
    public void setContent(List<ContentItem> content){
        this.content = content;
    }

    /**
     * This method return the of Content list entities
     * @return List of entities
     */
    public List<ContentItem> getContent(){
        return content;
    }
}