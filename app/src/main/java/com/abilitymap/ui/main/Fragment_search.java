package com.abilitymap.ui.main;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
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


        public Fragment_search() {
                // Required empty public constructor
        }

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
                adapter = new Search_ItemAdapter(itemList);
                recyclerView.setLayoutManager(layoutManager);
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
                return view;
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
        }



        @Override
        public void onItemClicked(int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
        }
}