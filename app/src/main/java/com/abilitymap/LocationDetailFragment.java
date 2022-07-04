package com.abilitymap;

import android.content.Context;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abilitymap.MainActivity;

public class LocationDetailFragment extends Fragment {
    MainActivity activity;
    
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = (MainActivity) getActivity();

    }
    public void onDetach() {
        super.onDetach();
        activity = null;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.location_detail, container, false);



        return rootView;
    }

}

