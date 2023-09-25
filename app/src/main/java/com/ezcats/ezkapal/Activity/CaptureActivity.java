package com.ezcats.ezkapal.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.Fragment.VerifikasiFragment;
import com.ezcats.ezkapal.Model.PemegangTicketModel;
import com.ezcats.ezkapal.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptureActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, VerifikasiFragment.HandleVerification {

    private ZXingScannerView mScannerView;
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;
    String token, name, email, number;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String type_account = sharedPreferences.getString(getString(R.string.type_account), "");
        token = sharedPreferences.getString(getString(R.string.token), "");
        name = sharedPreferences.getString(getString(R.string.name_shared_preference), "");
        email = sharedPreferences.getString(getString(R.string.email_shared_preference), "");
        number = sharedPreferences.getString(getString(R.string.email_shared_preference), "");

        if(savedInstanceState != null) {
            mFlash = savedInstanceState.getBoolean(FLASH_STATE, false);
            mAutoFocus = savedInstanceState.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = savedInstanceState.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = savedInstanceState.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }

        setContentView(R.layout.activity_capture);

        ViewGroup cameraFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        mScannerView.setFormats(formats);

        cameraFrame.addView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem;

        if(mFlash) {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off);
        }

        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);


        if(mAutoFocus) {
            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_off);
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_flash:
                mFlash = !mFlash;
                if(mFlash) {
                    item.setTitle(R.string.flash_on);
                } else {
                    item.setTitle(R.string.flash_off);
                }
                mScannerView.setFlash(mFlash);
                return true;
            case R.id.menu_auto_focus:
                mAutoFocus = !mAutoFocus;
                if(mAutoFocus) {
                    item.setTitle(R.string.auto_focus_on);
                } else {
                    item.setTitle(R.string.auto_focus_off);
                }
                mScannerView.setAutoFocus(mAutoFocus);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void handleResult(Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {}
        showMessageDialog("Contents = " + rawResult.getText() + ", Format = " + rawResult.getBarcodeFormat().toString());
        doTicket(rawResult.getText());

    }

    private void doTicket(String text) {
        TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        Call<ResponseBody> call = transactionService.getTicketData(token, "application/json", "XMLHttpRequest", text);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String message = jsonObject.getString("message");
                            if (message.equals("success")) {
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                PemegangTicketModel pemegangTicketModel =
                                        new PemegangTicketModel(dataObject.getInt("id_detail_pembelian"),
                                                dataObject.getInt("id_pembelian"),
                                                dataObject.getString("nama_pemegang_tiket"),
                                                dataObject.getString("kode_tiket"),
                                                dataObject.getString("status"),
                                                dataObject.getString("tanggal"),
                                                dataObject.getString("pelabuhan_asal"),
                                                dataObject.getString("pelabuhan_tujuan"),
                                                dataObject.getString("kapal"),
                                                dataObject.getString("waktu_asal"),
                                                dataObject.getString("waktu_tujuan")
                                        );
                                VerifikasiFragment verifikasiFragment = new VerifikasiFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("ticket_info", pemegangTicketModel);
                                bundle.putString("intentResults", text);
                                verifikasiFragment.setArguments(bundle);
                                verifikasiFragment.show(getSupportFragmentManager(), "VERIFIKASI_FRAGMENT");
                            } else {
                                Toast.makeText(getApplicationContext(), "Informasi tiket tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali, \njika kesalahan terjadi berulang, harap menghubungi administrator", Toast.LENGTH_SHORT).show();
                            Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API FAILED JSONEXCEPTION");
                            e.printStackTrace();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali, \njika kesalahan terjadi berulang, harap menghubungi administrator", Toast.LENGTH_SHORT).show();
                            Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API FAILED IOEXCEPTION");
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                        Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API NOT 200");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                    Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API NOT SUCCESSFUL");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API ON FAILURE");
            }
        });
    }

    public void showMessageDialog(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void verificationHandler() {
        mScannerView.resumeCameraPreview(this);
    }
}
