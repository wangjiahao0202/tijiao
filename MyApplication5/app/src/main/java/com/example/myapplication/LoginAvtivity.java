package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginAvtivity extends AppCompatActivity {
    EditText et_1,et_2;
    Button denglu;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_avtivity);
        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
        denglu = findViewById(R.id.btn_denglu);
        denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\r\n    \"username\":\""+et_1.getText().toString()+"\",\r\n    \"password\":\""+et_2.getText().toString()+"\"\r\n\r\n}");
                Request request = new Request.Builder()
                        .url("http://192.168.10.36/rest/account/login")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        /*if (response.isSuccessful()){*/
                            String string = response.body().string();
                            Log.d("TAG","onResponse"+string);
                            try {
                                JSONObject jsonObject = new JSONObject(string);
                                int code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginAvtivity.this,msg,Toast.LENGTH_SHORT).show();

                                    }
                                });
                                if (code == 200){
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    Intent intent = new Intent();
                                    intent.setClass(LoginAvtivity.this,MainActivity.class);
                                    startActivity(intent);
                                    String string2 = data.getString("token");
                                    Log.e("TAG",string2);
                                    token = string2;
                                    finish();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        /*}*/
                    }
                });
            }
        });
    }
}