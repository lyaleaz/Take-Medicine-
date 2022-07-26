package my.takeMedDesign.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import my.takeMedDesign.app.phMedPanel.phPanel_BottonNavigation;

public class CustomerLogin extends AppCompatActivity {

    TextInputLayout email,pass;
    Button Signin;
    TextView Forgotpassword , signup;
    FirebaseAuth Fauth;
    String emailid,pwd;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        try{
            email = (TextInputLayout)findViewById(R.id.EmailidCus);
            pass = (TextInputLayout)findViewById(R.id.Lpassword);
            Signin = (Button)findViewById(R.id.button4);
            signup = (TextView) findViewById(R.id.textView3);
            Forgotpassword = (TextView)findViewById(R.id.button4);

            Fauth = FirebaseAuth.getInstance();
            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();
                    if(isValid()){
                        final ProgressDialog mDialog = new ProgressDialog(CustomerLogin.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Sign In Please Wait.......");
                        mDialog.show();
                        Fauth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mDialog.dismiss();
                                    Toast.makeText(CustomerLogin.this, "Congratulation! You Have Successfully Login", Toast.LENGTH_LONG).show();
                                    if(Fauth.getCurrentUser()!=null){
                                      Fauth=FirebaseAuth.getInstance();

                                      databaseReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");
                                      databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                                              String role = snapshot.getValue(String.class);
                                              if(role.equals("Customer")){
                                                    startActivity(new Intent(CustomerLogin.this,cusPanel_BottonNavigation.class));
                                                    finish();
                                              }else if(role.equals("Pharmtic")){
                                                  startActivity(new Intent(CustomerLogin.this, phPanel_BottonNavigation.class));
                                                  finish();
                                              }else if(role.equals("Delivery")) {
                                                  startActivity(new Intent(CustomerLogin.this,DeliveryPanel_BottomNavigation.class));
                                                  finish();
                                              }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(CustomerLogin.this,error.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });
                        }else {

                            Intent intent = new Intent(CustomerLogin.this, my.takeMedDesign.app.MainMenu.class);
                            startActivity(intent);
                            finish();
                        }

//                                    Intent Z = new Intent(CustomerLogin.this,cusPanel_BottonNavigation.class);
//                                    startActivity(Z);
//                                    finish();
                                }
                                else {
                                    mDialog.dismiss();
                                    Toast.makeText(CustomerLogin.this, "Error!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

//

                    }

                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CustomerLogin.this,CustomerRegistration.class));
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    String emailpattern  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){

        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isvalid=false,isvalidemail=false,isvalidpassword=false;
        if(TextUtils.isEmpty(emailid)){
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }else{
            if(emailid.matches(emailpattern)){
                isvalidemail=true;
            }else{
                email.setErrorEnabled(true);
                email.setError("Invalid Email Address");
            }
        }
        if(TextUtils.isEmpty(pwd)){

            pass.setErrorEnabled(true);
            pass.setError("Password is Required");
        }else{
            isvalidpassword=true;
        }
        isvalid=(isvalidemail && isvalidpassword)?true:false;
        return isvalid;
    }
}