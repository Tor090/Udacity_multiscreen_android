/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.os.Bundle;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.android.miwok.adapters.SampleFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


//        TextView numbers = findViewById(R.id.numbers);
//        TextView family = findViewById(R.id.family);
//        TextView colors = findViewById(R.id.colors);
//        TextView phrases = findViewById(R.id.phrases);
//
//        View.OnClickListener onClickListener = view -> {
//            Intent intent;
//            switch (view.getId()){
//                case R.id.numbers:
//                    intent = new Intent(MainActivity.this,NumbersActivity.class);
//                    break;
//                case R.id.family:
//                    intent = new Intent(MainActivity.this,FamilyActivity.class);
//                    break;
//                case R.id.colors:
//                    intent = new Intent(MainActivity.this,ColorsActivity.class);
//                    break;
//                case R.id.phrases:
//                    intent = new Intent(MainActivity.this,PhrasesActivity.class);
//                    break;
//                default:
//                    throw new IllegalStateException("Unexpected value: " + view.getId());
//            }
//            startActivity(intent);
//        };
//
//        numbers.setOnClickListener(onClickListener);
//        family.setOnClickListener(onClickListener);
//        colors.setOnClickListener(onClickListener);
//        phrases.setOnClickListener(onClickListener);
    }

}
