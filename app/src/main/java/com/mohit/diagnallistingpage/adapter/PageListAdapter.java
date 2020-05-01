package com.mohit.diagnallistingpage.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mohit.diagnallistingpage.R;
import com.mohit.diagnallistingpage.data.remote.response.page.ContentItem;
import com.mohit.diagnallistingpage.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mohit.diagnallistingpage.util.Utils.removeExtension;

/**
 * This class represents the Page list recyclerview adapter
 * <p>
 * Author: Mohit issar
 * Email: 007mohitissar@gmail.com
 * Created: 05/01/2020
 * Modified: 05/01/2020
 */
public class PageListAdapter extends RecyclerView.Adapter<PageListAdapter.ViewHolder> implements Filterable {

    public List<ContentItem> contentItemList;
    private List<ContentItem> contentListFiltered;
    private PageListAdapterListener listener;
    private String searchQuery;
    private Context context;

    public PageListAdapter(List<ContentItem> contentItemList, PageListAdapterListener listener, Context context) {
        this.contentItemList = contentItemList;
        this.contentListFiltered = contentItemList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public PageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_item, parent, false);

        return new PageListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PageListAdapter.ViewHolder holder, int position) {
        ContentItem contentItem = contentListFiltered.get(position);
        RequestOptions requestOptions = new RequestOptions();
        Uri uri = Uri.parse(context.getString(R.string.drawable_path) + removeExtension(contentItem.getPosterImage()));
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.placeholder_for_missing_posters)
                .apply(requestOptions)
                .into(holder.posterIv);

        if (searchQuery != null) {
            SpannableStringBuilder sb = new SpannableStringBuilder(contentItem.getName());
            Pattern word = Pattern.compile(searchQuery.toLowerCase());
            Matcher match = word.matcher(contentItem.getName().toLowerCase());

            while (match.find()) {
                ForegroundColorSpan fcs = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorYellow)); //specify color here
                sb.setSpan(fcs, match.start(), match.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
            holder.posterTitletv.setText(sb);
        } else {
            holder.posterTitletv.setText(contentItem.getName());
        }
    }

    @Override
    public int getItemCount() {
        return contentListFiltered.size();
    }

    public void refreshAdapter(List<ContentItem> contentItemList) {
        this.contentListFiltered = contentItemList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    searchQuery = null;
                    contentListFiltered = contentItemList;
                } else {
                    if (charSequence.length() > 2) {
                        searchQuery = charString;
                        List<ContentItem> filteredList = new ArrayList<>();
                        for (ContentItem row : contentItemList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name
                            if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        contentListFiltered = filteredList;
                    } else {
                        searchQuery = null;
                        contentListFiltered = contentItemList;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contentListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contentListFiltered = (ArrayList<ContentItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface PageListAdapterListener {
        void onContentSelected(ContentItem contentItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.poster_iv)
        ImageView posterIv;
        @Nullable
        @BindView(R.id.poster_title_tv)
        TextView posterTitletv;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected content in callback
                    listener.onContentSelected(contentListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
}
