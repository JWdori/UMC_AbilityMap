package com.abilitymap.ui.main;
import android.os.*;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abilitymap.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_search extends Fragment implements Search_ItemAdapter.onItemListener {
        ImageView searchback;
        SearchView searchView;
        private Search_ItemAdapter adapter;
        private List<Search_Item> itemList;







        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.search, container, false);

                searchView=view.findViewById(R.id.search_view);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                                return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                                adapter.getFilter().filter(newText);
                                return false;
                        }
                });



                RecyclerView recyclerView = view.findViewById(R.id.search_result);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

                //adapter
                itemList = new ArrayList<>(); //샘플테이터
                fillData();
                recyclerView.setLayoutManager(layoutManager);
                adapter = new Search_ItemAdapter(itemList);

                recyclerView.setAdapter(adapter);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL); //밑줄
                recyclerView.addItemDecoration(dividerItemDecoration);

                //데이터셋변경시
                //adapter.dataSetChanged(exampleList);

                //어댑터의 리스너 호출
                adapter.setOnClickListener(this);

                searchback = view.findViewById(R.id.search_back);
                searchback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().remove(Fragment_search.this).commit();
                                fragmentManager.popBackStack();
                        }
                });

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                                int totalCount = recyclerView.getAdapter().getItemCount();

                                if(lastPosition == totalCount){
                                        fillData();
                                }
                        }
                });



                return view;
        }



        public void onStart() {
                super.onStart();
                Log.d("MainActivity_", "onStart");
                fillData();
        }


        @Override
        public void onLoadMore() {
                Log.d("MainActivity_", "onLoadMore");
                adapter.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                itemList.clear();
                                adapter.setProgressMore(false);

                                ///////이부분에을 자신의 프로젝트에 맞게 설정하면 됨
                                //다음 페이지? 내용을 불러오는 부분
                                int start = adapter.getItemCount();
                                int end = start + 15;
                                for (int i = start + 1; i <= end; i++) {
                                        itemList.add(new Search_Item(R.drawable.hos_icon, "추가임!", "Ten"));
                                }
                                //////////////////////////////////////////////////
                                adapter.addItemMore(itemList);
                                adapter.setMoreLoading(false);
                        }
                }, 2000);
        }




        private void fillData() {
                itemList = new ArrayList<>(); //샘플테이터
                itemList.add(new Search_Item(R.drawable.hos_icon, "One", "Ten"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Two", "Eleven"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Three", "Twelve"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Four", "Thirteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Five", "Fourteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Six", "Fifteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Seven", "Sixteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Eight", "Seventeen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Nine", "Eighteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Nine", "Eighteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "One", "Ten"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Two", "Eleven"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Three", "Twelve"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Four", "Thirteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Five", "Fourteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Six", "Fifteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Seven", "Sixteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Eight", "Seventeen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Nine", "Eighteen"));
                itemList.add(new Search_Item(R.drawable.hos_icon, "Nine", "Eighteen"));
        }



        @Override
        public void onItemClicked(int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
        }
}