package com.example.kiszeldaniel_pcjkbh;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;
    private final int[] images = {R.drawable.bb1, R.drawable.bb2, R.drawable.bb3, R.drawable.bb4, R.drawable.bb5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 mozgoKep = findViewById(R.id.mozgoKep);

        MozgoKep adapter = new MozgoKep(new int[]{R.drawable.bb1, R.drawable.bb2, R.drawable.bb3, R.drawable.bb4, R.drawable.bb5});
        mozgoKep.setAdapter(adapter);

        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                mozgoKep.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000);
            }
        };

        handler.postDelayed(runnable, 3000);

        mozgoKep.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
        });

    }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(runnable);
        }


}
