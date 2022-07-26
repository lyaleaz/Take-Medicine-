package my.takeMedDesign.app;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.takeMedDesign.app.cusPanel.cus_homeFragmnet;
import my.takeMedDesign.app.cusPanel.cus_profileFragment;
import my.takeMedDesign.app.cusPanel.cus_shoppingFragmnet;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class cusPanel_BottonNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_panel_bottom_navigatoin);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name=getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        if(name!=NULL){
//            if(name.equalsIgnoreCase("Homepage")){
//                loadphfragment(new cus_homeFragmnet());
//            }
//            else if(name.equalsIgnoreCase("ThankyouPage")){
//                loadphfragment(new cus_homeFragmnet());
//            }
//        }else{
//            loadphfragment(new cus_homeFragmnet());
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()){
            case R.id.cusHome:
                fragment=new cus_homeFragmnet();
                break;
        }
        switch (item.getItemId()){
            case R.id.shopping:
                fragment=new cus_shoppingFragmnet();
                break;
        }
        switch (item.getItemId()){
            case R.id.cusProfile:
                fragment=new cus_profileFragment();
                break;
        }
        return loadphfragment(fragment);


    }

    private boolean loadphfragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }
}
