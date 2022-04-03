package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.ezcats.ezkapal.R;

public class PenumpangSelector extends DialogFragment {
    
    NumberPicker numberPicker;
    Button mButtonSubmitValuePenumpang;
    int currentValue;

    private static final String TAG = "PENUMPANG_SELECTOR";

    public interface SendValue{
        void sendJumlahPenumpang(int number);
    }

    public  SendValue mSendValue;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mSendValue = (SendValue) getTargetFragment();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: "+e.getMessage());
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_penumpang_selector, container, false);
        numberPicker = v.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        Bundle bundle = getArguments();
        int penumpangValue = bundle.getInt("penumpangValue");
        Log.d(TAG, "onCreateView: "+penumpangValue);
        numberPicker.setValue(penumpangValue);
        
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int newVal) {
                Log.d(TAG, "onValueChange: "+newVal);
                currentValue = newVal;
            }
        });

        mButtonSubmitValuePenumpang = v.findViewById(R.id.button_submit_value_penumpang);
        mButtonSubmitValuePenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSendValue.sendJumlahPenumpang(currentValue);
                getDialog().dismiss();
            }
        });
        return v;
    }
}