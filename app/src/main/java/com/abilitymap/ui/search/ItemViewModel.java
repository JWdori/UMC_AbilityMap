package com.abilitymap.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<Double> selectedLat;
    private MutableLiveData<Double> selectedLng;

    public MutableLiveData<Double> getSelectedLat() {
        return selectedLat;
    }
    public void setSelectedLat(MutableLiveData<Double> selectedLat) {
        this.selectedLat = selectedLat;
    }

    public MutableLiveData<Double> getSelectedLng() {
        return selectedLng;
    }

    public void setSelectedLng(MutableLiveData<Double> selectedLng) {
        this.selectedLng = selectedLng;
    }
}
