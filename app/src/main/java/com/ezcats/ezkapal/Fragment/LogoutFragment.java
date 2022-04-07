package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutFragment extends DialogFragment {

    private static final String TAG = "LOGOUT_FRAGMENT";
    Button btn_logout, btn_cancel;
    String token;
    SharedPreferences sharedPreferences;

    public interface LogoutListener{
        void logout();
    }

    public LogoutListener logoutListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            logoutListener = (LogoutListener) getTargetFragment();
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
            float width = getResources().getDimension(R.dimen.logout_width);
            float height = getResources().getDimension(R.dimen.logout_height);
            dialog.getWindow().setLayout(Math.round(width), Math.round(height));
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        btn_logout = v.findViewById(R.id.lanjutkan_logout);
        btn_cancel = v.findViewById(R.id.cancel_logout);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
                Call<ResponseBody> call = accountService.logoutUser("application/json","XMLHttpRequest", token);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            if(response.code()==200){
                                logoutListener.logout();
                                getDialog().dismiss();
                            }
                        }
                        if (response.code()==401){
                            logoutListener.logout();
                            getDialog().dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(),"Harap periksa internet anda", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return v;
    }
}