package com.abilitymap.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import com.naver.maps.geometry.LatLng;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<LatLng> selectedLatLng;
    private MutableLiveData<String> selectedTag;


    public MutableLiveData<LatLng> getSelectedLatLng(){
        if(selectedLatLng == null){
            selectedLatLng = new MutableLiveData<LatLng>();
        }

        return selectedLatLng;
    }

}
