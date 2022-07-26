package my.takeMedDesign.app.phMedPanel;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.takeMedDesign.app.R;

public class phPanel_BottonNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.ph_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch(item.getItemId()){
            case R.id.phHome:
                fragment=new PhHomeFragment();
                break;
            case R.id.pendingOrder:
                fragment=new PhPendingOrderFragment();
                break;
            case R.id.phOrder:
                fragment=new PhOrderFragment();
                break;
            case R.id.phAccount:
                fragment=new PhProfileFregment();

        }
        return loadphfragment(fragment);
    }

    private boolean loadphfragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }
}
