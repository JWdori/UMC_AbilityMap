package com.abilitymap.ui.main;
import static com.abilitymap.ui.main.MainActivity.charge_list;
import static com.abilitymap.ui.main.MainActivity.fac_list;
import static com.abilitymap.ui.main.MainActivity.hos_list;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abilitymap.R;
import com.abilitymap.api.JsonApi_charge;
import com.abilitymap.api.JsonApi_fac;
import com.abilitymap.api.JsonApi_hos;
import com.abilitymap.ui.marker.LocationBottomSheet;
import com.abilitymap.ui.search.ItemViewModel;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class Fragment_search extends Fragment implements Search_ItemAdapter.onItemListener {
        ImageView searchback;
        SearchView searchView;
        private Search_ItemAdapter adapter;
        private List<Search_Item> list2;
        private boolean isLoading = false;
        private ArrayList<Search_Item> itemList = new ArrayList<>();
        int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
        ProgressDialog dialog;

        private ItemViewModel viewModel;

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

                itemList = new ArrayList<Search_Item>();

//                recyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);

                adapter = new Search_ItemAdapter(this.itemList);
                adapter.setLinearLayoutManager(mLayoutManager);

                adapter.setRecyclerView(recyclerView);
                recyclerView.setAdapter(adapter);

                adapter.setOnClickListener(this);


                viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);


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
        public void onStart() {
                super.onStart();
                loadData();
        }

        @Override
        public void onLoadMore() {
                new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                                itemList.add(null);
                                adapter.items.add(null);
                                adapter.notifyItemInserted(itemList.size() - 1);
                        }
                });
                dialog = new ProgressDialog(getContext()); //프로그레스 대화상자 객체 생성
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //프로그레스 대화상자 스타일 원형으로 설정
                dialog.setCancelable(false);
                dialog.setMessage("정보를 가져오는 중입니다.\n잠시만 기다려주세요."); //프로그레스 대화상자 메시지 설정
                dialog.show(); //프로그레스 대화상자 띄우기

                Handler handler = new Handler();
//                adapter.setProgressMore(true);
                handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                                adapter.setProgressMore(false);
                                itemList.remove(itemList.size() - 1);
                                adapter.items.remove(adapter.items.size()-1);
                                int scrollPosition = itemList.size();
                                adapter.notifyItemRemoved(scrollPosition);
                                ///////이부분에을 자신의 프로젝트에 맞게 설정하면 됨
                                //다음 페이지? 내용을 불러오는 부분
//                                int start = adapter.getItemCount();
//                                int end = start + 20;
                                itemList.clear();
//                                for (int i = start + 1; i <= end; i++) {
//
////                                        list2.add(new Search_Item(R.drawable.hos_icon, "추가임!", "Ten", "이게문제엿네"));
//                                }
                                adapter.addItemMore(itemList);
                                adapter.notifyDataSetChanged();
                                adapter.setMoreLoading(false);
                                dialog.dismiss();
                        }
                }, 1000);
        }




        private void loadData() {
                itemList = new ArrayList<>();
                String name;
                String location;
                String tag;
                double latitude ;
                double longitude ;

                JsonApi_hos.hos_item selectedItem = null;
                for (int i = 0; i<charge_list.size();i++) {
                        JsonApi_charge.charge_item item = charge_list.get(i);
                        location = item.getLocation();
                        latitude = Double.parseDouble(item.getLat());
                        longitude = Double.parseDouble(item.getLng());
                        name = ((MainActivity) getActivity()).getSimpleCurrentAddress(
                                ((MainActivity) getActivity()).getCurrentAddress(latitude,longitude));
                        tag = "전동휠체어 급속충전기";
                        itemList.add(new Search_Item(R.drawable.charge_icon, location, name, tag));
                }
                for (int i = 0; i<hos_list.size();i++) {
                        JsonApi_hos.hos_item item = hos_list.get(i);
                        selectedItem = item;
                        name = item.getName();
                        location = item.getLocation();
                        tag = "보건의료시설";
                        itemList.add(new Search_Item(R.drawable.hos_icon, name, location, tag));
                }
                for (int i = 0; i<fac_list.size();i++) {
                        JsonApi_fac.fac_item item = fac_list.get(i);
                        name = item.getName();
                        location = item.getLocation();
                        tag = "공공/복지시설";
                        itemList.add(new Search_Item(R.drawable.facility_office, name, location, tag));
                }
                for (int i = 1; i <= 11; i++) {
                        itemList.add(new Search_Item(R.drawable.hos_icon, "하나의원", "점심뭐먹지","병원아님"));
                        itemList.add(new Search_Item(R.drawable.hos_icon, "참안과", "자고싶다","병원아님"));
                        // 충전기 병원 관공서 지도에서 마커를 클릭할때 그 페이지가 뜨는게,
                        // 위험제보
                }
                adapter.addAll2(itemList);

        }

        //다시 만들기 귀찮아서 일단 재활용
        JsonApi_hos.hos_item findThisTotalMarkerItem(ArrayList<JsonApi_hos.hos_item> list) {
                JsonApi_hos.hos_item selectedItem = null;
                for (int i = 0; i < list.size(); i++) {
                        JsonApi_hos.hos_item item = list.get(i);
                                selectedItem = item;
                }
                return selectedItem;
        }
        JsonApi_fac.fac_item findThisFacilityMarkerItem(ArrayList<JsonApi_fac.fac_item> list) {
                JsonApi_fac.fac_item selectedItem = null;
                for (int i = 0; i < list.size(); i++) {
                        JsonApi_fac.fac_item item = list.get(i);
                        selectedItem = item;
                }
                return selectedItem;
        }
        JsonApi_charge.charge_item findThisChargerMarkerItem(ArrayList<JsonApi_charge.charge_item> list) {
                JsonApi_charge.charge_item selectedItem = null;
                for (int i = 0; i < list.size(); i++) {
                        JsonApi_charge.charge_item item = list.get(i);
                        selectedItem = item;
                }
                return selectedItem;
        }


        @Override
        public void onItemClicked(int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();


                viewModel.setSelectedLng();

        }
}