package com.unsw.valerie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;
    private HomeFragment mHomeFragment;
    private CollFragment mCollFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_search:
                        onSelect(0);
                        break;
                    case R.id.menu_coll:
                        onSelect(1);
                        break;

                    default:
                }
                return true;
            }
        });
        bottomBar.setSelectedItemId(bottomBar.getMenu().getItem(0).getItemId());
    }

    private void onSelect(int i) {
        if (i==0){
            if (mHomeFragment == null) {
                mHomeFragment = HomeFragment.newInstance();
            }
            switchFragment(mHomeFragment);
        }else if (i==1){
            if (mCollFragment == null) {
                mCollFragment = CollFragment.newInstance();
            }
            switchFragment(mCollFragment);
        }

    }
    private void switchFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mCollFragment != null) {
            transaction.hide(mCollFragment);
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.container, fragment);
        }
        transaction.show(fragment).commit();
    }
}
