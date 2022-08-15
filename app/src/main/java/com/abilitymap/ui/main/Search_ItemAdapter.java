package com.abilitymap.ui.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abilitymap.R;

import java.util.ArrayList;
import java.util.List;

public class Search_ItemAdapter extends RecyclerView.Adapter<Search_ItemAdapter.ItemViewHolder> implements Filterable {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<Search_Item> mDataList;
    private List<Search_Item> mDataListAll;
    private List<Search_Item> rDataList;
    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
    private LinearLayoutManager mLinearLayoutManager;
    private onItemListener onLoadMoreListener;
    private int visibleThreshold = 1;
    private boolean isMoreLoading = false;




    public Search_ItemAdapter(onItemListener onLoadMoreListener) {
        this.onLoadMoreListener=onLoadMoreListener;
        mDataList =new ArrayList<>();
        mDataListAll = new ArrayList<>();
    }


    //interface - 클릭인터페이스
    private onItemListener mListener;
    public void setOnClickListener(onItemListener listener){
        mListener = listener;
    }

    //data set changed
    public void dataSetChanged(List<Search_Item> exampleList) {
        mDataList = exampleList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    //1.onCreateViewHolder -------------------------------------------------------
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == VIEW_ITEM) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }

    }

    //2.onBindViewHolder  -------------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Search_Item currentItem = mDataList.get(position);
        // TODO : 데이터를 뷰홀더에 표시하시오
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getText1());
        holder.textView2.setText(currentItem.getText2());
        holder.textView2.setText(currentItem.getText3());
        // TODO : 리스너를 정의하시오.
        if (mListener != null){
            final int pos = position;
            //final ItemModel item = mItems.get(viewHolder.getAdapterPosition());
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(position);
                    //mListener.onItemClicked(item);
                }
            });
            //버튼등에도 동일하게 지정할 수 있음 holder.버튼이름.setOnClickListener..형식으로
        }
    }

    //3.getItemCount  -------------------------------------------------------
    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    // 데이터 필터 검색 Filterable implement ---------------------------------
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Search_Item> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Search_Item item : mDataListAll) {
                    //TODO filter 대상 setting
                    if (item.getText1().toLowerCase().contains(filterPattern)||
                            item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataList.clear();
            mDataList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    // 뷰홀더 클래스  ---------------------------------
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        // TODO : 뷰홀더 완성하시오
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;

        ItemViewHolder(View itemView) {
            super(itemView);
            // TODO : 뷰홀더 완성하시오
            imageView = itemView.findViewById(R.id.search_marker);
            textView1 = itemView.findViewById(R.id.search_title);
            textView2 = itemView.findViewById(R.id.search_location);
            textView3 = itemView.findViewById(R.id.search_tag);
        }
    }
    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager){
        this.mLinearLayoutManager=linearLayoutManager;
    }
    //onclick listener interface
    //1. interface onItemListener 선언
    //2. 내부에서 mListener 선언하고
    // 외부에서 접근가능하도록 setOnClickListener작성
    //3.onBindViewHolder에서 처리
    public interface onItemListener {
        void onLoadMore();
        void onItemClicked(int position);
        //void onItemClicked(ItemModel model); 모델값을 넘길수 있음
        //다른버튼도 정의할 수 있음 onShareButtonClicked(int position);
    }


    public void setRecyclerView(RecyclerView mView){
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
//                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }


            }
        });
    }




    public void addAll(List<Search_Item> lst){
        mDataList.clear();
        mDataList.addAll(lst);
        notifyDataSetChanged();
    }



    public void addItemMore(List<Search_Item> lst){
        mDataList.addAll(lst);
        notifyItemRangeChanged(0,mDataList.size());
    }



    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading=isMoreLoading;
    }




    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mDataList.add(null);
                    notifyItemInserted(mDataList.size() - 1);
                }
            });
        } else {
            mDataList.remove(mDataList.size() - 1);
            notifyItemRemoved(mDataList.size());
        }
    }

    static class ProgressViewHolder extends ItemViewHolder {
        public ProgressBar pBar;
        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }


}