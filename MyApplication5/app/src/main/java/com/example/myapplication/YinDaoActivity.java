package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.fragment.BlankFragment;
import com.example.myapplication.fragment.BlankFragment2;
import com.example.myapplication.fragment.BlankFragment3;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class YinDaoActivity extends AppCompatActivity {
    List<Fragment> fragments;
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_dao);
        vp = findViewById(R.id.vp);
        fragments = new ArrayList<>();
        fragments.add(new BlankFragment());
        fragments.add(new BlankFragment2());
        fragments.add(new BlankFragment3());
        vp.setAdapter(new myadapter(getSupportFragmentManager()));
    }
    public class myadapter extends FragmentStatePagerAdapter{

        public myadapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}