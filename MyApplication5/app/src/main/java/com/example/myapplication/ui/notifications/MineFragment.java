package com.example.myapplication.ui.notifications;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.LoginAvtivity;
import com.example.myapplication.R;
import com.example.myapplication.FangshuActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView iv;
    TextView tv_1;
    TextView tv_2;
    TextView tv_shuzi;
    TextView tv_shebeizhu;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_mine, container, false);
        iv = inflate.findViewById(R.id.iv_tupian);
        tv_1 = inflate.findViewById(R.id.tv_1);
        tv_2 = inflate.findViewById(R.id.tv_2);
        tv_shuzi = inflate.findViewById(R.id.shuzi);
        tv_shebeizhu = inflate.findViewById(R.id.shebeishu);
        tv_shuzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FangshuActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://192.168.10.36/rest/account/accountInfo")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("authorization",LoginAvtivity.token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String string = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        JSONObject data = jsonObject.optJSONObject("data");
                        URL url = new URL("http://192.168.10.36/"+ data.getString("avatar"));
                        Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                    /*String nick_name = data.getString("nick_name");
                    String signature = data.getString("signature");*/
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_1.setText(data.optString("nick_name"));
                                tv_2.setText(data.optString("signature"));
                                iv.setImageBitmap(bitmap);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });



        Request request1 = new Request.Builder()
                .url("http://192.168.10.36/rest/house/listUserAllHouses")
                .method("POST", body)
                .addHeader("authorization", LoginAvtivity.token)
                .build();
            client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray data = jsonObject.getJSONArray("data");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_shuzi.setText(""+data.length());

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}