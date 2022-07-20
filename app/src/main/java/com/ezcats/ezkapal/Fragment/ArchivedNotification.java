package com.ezcats.ezkapal.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.Activity.TransaksiTerkiniActivity;
import com.ezcats.ezkapal.Adapter.NotificationAdapter;
import com.ezcats.ezkapal.MainActivity;
import com.ezcats.ezkapal.Model.JSONModel.NotificationJSONModel;
import com.ezcats.ezkapal.Model.JSONModel.NotificationUpdateJSONModel;
import com.ezcats.ezkapal.Model.NotificationModel;
import com.ezcats.ezkapal.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchivedNotification extends Fragment implements NotificationAdapter.OnNotificationListener, NotificationAdapter.OnOpenNotification {

    Context mContext;
    RecyclerView recyclerView;
    String token;
    SharedPreferences sharedPreferences;
    ConstraintLayout constraintLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_archived_notification, container, false);
        init(v);
        initData();
        return v;
    }

    private void initData() {
        AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
        Call<NotificationJSONModel> call = accountService.getArchivedNotification("application/json","XMLHttpRequest",token);
        call.enqueue(new Callback<NotificationJSONModel>() {
            @Override
            public void onResponse(Call<NotificationJSONModel> call, Response<NotificationJSONModel> response) {
                if(response.code() == 200){
                    initRecycler(response.body().getNotificationModel());
                    if(response.body().getNotificationModel().size()==0){
                        constraintLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    broadcastToast("Sepertinya terjadi kesalahan, harap ulangi kembali!");
                }
            }

            @Override
            public void onFailure(Call<NotificationJSONModel> call, Throwable t) {
                broadcastToast("Terjadi kesalahan, harap ulangi kembali!");
            }
        });
    }

    private void broadcastToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    private void initRecycler(List<NotificationModel> notificationModel) {
        NotificationAdapter notificationAdapter = new NotificationAdapter(mContext, notificationModel, this, this);
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void init(View v) {
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference),Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");
        mContext = getActivity().getApplicationContext();
        recyclerView = v.findViewById(R.id.archived_notification_recycler);
        constraintLayout = v.findViewById(R.id.constraint_archived_notification_not_found);
    }

    @Override
    public void OnClickNotification(int id, boolean action) {
        AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
        Call<NotificationUpdateJSONModel> call = accountService.updateNotification("application/json","XMLHttpRequest",token, id, action);
        call.enqueue(new Callback<NotificationUpdateJSONModel>() {
            @Override
            public void onResponse(Call<NotificationUpdateJSONModel> call, Response<NotificationUpdateJSONModel> response) {
                if(response.code()==200){
                    initData();
                } else {
                    broadcastToast("Sepertinya terjadi kesalahan, harap ulangi kembali!");
                }
            }

            @Override
            public void onFailure(Call<NotificationUpdateJSONModel> call, Throwable t) {
                broadcastToast("Sepertinya terjadi kesalahan, harap ulangi kembali!");
            }
        });
    }

    @Override
    public void OnSelectNotification(String click_action) {
        switch (click_action){
            case "main_menu":
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                break;
            case "transaction":
                Intent intent1 = new Intent(getContext(), TransaksiTerkiniActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}