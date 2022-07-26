package my.takeMedDesign.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {
    Button singnwithEmail,signup;
    ImageView back2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        singnwithEmail=(Button)findViewById(R.id.singnwithEmail);
        signup=(Button)findViewById(R.id.signup);

        singnwithEmail.setOnClickListener((v) -> {
            Intent singnwithEmail=new Intent(MainMenu.this,CustomerLogin.class);
            singnwithEmail.putExtra("Home","Email");
            startActivity(singnwithEmail);
            finish();
        });
        signup.setOnClickListener((v) -> {
            Intent signup=new Intent(MainMenu.this,ChooseOne.class);
            signup.putExtra("Home","SignUp");
            startActivity(signup);
            finish();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}