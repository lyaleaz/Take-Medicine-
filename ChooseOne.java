package my.takeMedDesign.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseOne extends AppCompatActivity {
    Button Customer,Delivery;
    Intent intent;
    String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        intent=getIntent();
        type =intent.getStringExtra("Home").toString().trim();

        Delivery=(Button)findViewById(R.id.delivery);
        Customer=(Button)findViewById(R.id.customer);

        Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Email")){
                    Intent singnwithEmailCust = new Intent(ChooseOne.this,CustomerLogin.class);
                    startActivity(singnwithEmailCust);
                    finish();
                }
                if(type.equals("SignUp")){
                    Intent Registercust = new Intent(ChooseOne.this,CustomerRegistration.class);
                    startActivity(Registercust);

                }

            }
        });
        Delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Email")){
                    Intent singnwithEmailDev = new Intent(ChooseOne.this,DeliveryLogin.class);
                    startActivity(singnwithEmailDev);
                    finish();
                }
                if(type.equals("SignUp")){
                    Intent RegisterDev = new Intent(ChooseOne.this,DeliveryrRegistration.class);
                    startActivity(RegisterDev);

                }

            }
        });
    }
}