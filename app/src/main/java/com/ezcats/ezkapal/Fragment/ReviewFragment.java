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
import android.widget.EditText;
import android.widget.RatingBar;

import com.ezcats.ezkapal.R;

public class ReviewFragment extends DialogFragment {

    private static final String TAG = "REVIEW_LAYANAN";

    EditText isiReview;
    Button upReview, hapusReview;
    RatingBar ratingReview;
    private int score;
    private int id_detail;

    public interface ReviewData{
        void sendReview(int id_detail, String review, int score);
        void deleteData(int id_detail);
    }

    public ReviewData reviewData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            reviewData = (ReviewData) getActivity();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: "+e.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_review, container, false);
        isiReview = v.findViewById(R.id.isi_review);
        ratingReview = v.findViewById(R.id.rating_bar);
        upReview = v.findViewById(R.id.up_review);
        hapusReview = v.findViewById(R.id.hapus_review);

        Bundle bundle = getArguments();
        this.id_detail = bundle.getInt("id_detail",0);
        isiReview.setText(bundle.getString("review"));
        int ratingBefore = bundle.getInt("score", 0);
        ratingReview.setRating(Float.parseFloat(String.valueOf(ratingBefore)));
        Log.d(TAG, "onCreateView: rating on getRating()"+ratingReview.getRating());

        ratingReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float starValue, boolean b) {
                score = (int) Math.round(starValue);
                Log.d(TAG, "onRatingChanged: "+score);
            }
        });


        upReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewData.sendReview(id_detail, isiReview.getText().toString(), score);
                getDialog().dismiss();
            }
        });

        hapusReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewData.deleteData(id_detail);
                getDialog().dismiss();
            }
        });
        return v;
    }
}