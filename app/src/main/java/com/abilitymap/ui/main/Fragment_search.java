package com.abilitymap.ui.main;
import android.content.ClipData;
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
                //서치뷰 검색
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

                //
                RecyclerView recyclerView = view.findViewById(R.id.search_result);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                itemList = new ArrayList<>(); //샘플테이터
                loadData();
                adapter = new Search_ItemAdapter(this);
                adapter.setLinearLayoutManager(mLayoutManager);
                adapter.setRecyclerView(recyclerView);
                recyclerView.setAdapter(adapter);
                adapter.setOnClickListener(this);





                //뒤로가기 버튼
                searchback = view.findViewById(R.id.search_back);
                searchback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().remove(Fragment_search.this).commit();
                                fragmentManager.popBackStack();
                        }
                });


                return view;
        }



        @Override
        public void onLoadMore() {
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
                                        itemList.add(new Search_Item(R.drawable.hos_icon, "추가임!", "Ten", "이게문제엿네"));
                                }
                                //////////////////////////////////////////////////
                                adapter.addItemMore(itemList);
                                adapter.setMoreLoading(false);
                        }
                }, 500);
        }




        private void loadData() {
                itemList = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                        itemList.add(new Search_Item(R.drawable.hos_icon, "Nine", "Eighteen","병원아님"));
                }
        }

        @Override
        public void onItemClicked(int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
        }
}