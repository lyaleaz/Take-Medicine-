package my.takeMedDesign.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import my.takeMedDesign.app.cusPanel.Customer;

public class CustomerRegistration extends AppCompatActivity {

    TextInputLayout Fname, Lname, Email, Pass, cpass, mobileno, Location, Id, BirthDay;
    Spinner Statespin, Cityspin;
    Button Signup, Emaill, Phone;
    //CountryCodePicker Cpp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname, lname, emailid, password, confpassword, mobile, location, id, birth;
    String role = "Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        Fname = (TextInputLayout) findViewById(R.id.FirstNameCus);
        Lname = (TextInputLayout) findViewById(R.id.LastNameCus);
        Email = (TextInputLayout) findViewById(R.id.EmailidCus);
        Pass = (TextInputLayout) findViewById(R.id.PasswordCus);
        cpass = (TextInputLayout) findViewById(R.id.confirmpassCus);
        mobileno = (TextInputLayout) findViewById(R.id.PhoneCus);
        Location = (TextInputLayout) findViewById(R.id.houseNoCus);
        Id = (TextInputLayout) findViewById(R.id.IDCus);
        BirthDay = (TextInputLayout) findViewById(R.id.birthCus);


        databaseReference = firebaseDatabase.getInstance().getReference("Customer");
        FAuth = FirebaseAuth.getInstance();

        Signup = (Button) findViewById(R.id.SignupCus);
        Signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 fname = Fname.getEditText().getText().toString().trim();
                 lname = Lname.getEditText().getText().toString().trim();
                 emailid = Email.getEditText().getText().toString().trim();
                 mobile = mobileno.getEditText().getText().toString().trim();
                 password = Pass.getEditText().getText().toString().trim();
                 confpassword = cpass.getEditText().getText().toString().trim();
                 location = Location.getEditText().getText().toString().trim();
                 id = Id.getEditText().getText().toString().trim();
                 birth = BirthDay.getEditText().getText().toString().trim();
                 if (isValid()){
                     final ProgressDialog mDialog = new ProgressDialog(CustomerRegistration.this);
                     mDialog.setCancelable(false);
                     mDialog.setCanceledOnTouchOutside(false);
                     mDialog.setMessage("Registration in progress please wait......");
                     mDialog.show();

                     FAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {

                             if (task.isSuccessful()){
                                 String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                 databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                 final HashMap<String , String> hashMap = new HashMap<>();
                                 hashMap.put("Role",role);
                                 databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {

                                         HashMap<String , String> hashMap1 = new HashMap<>();
                                         hashMap1.put("Mobile No",mobile);
                                         hashMap1.put("First Name",fname);
                                         hashMap1.put("Last Name",lname);
                                         hashMap1.put("EmailId",emailid);
                                         hashMap1.put("Location",location);
                                         hashMap1.put("Id",id);
                                         hashMap1.put("Password",password);
                                         hashMap1.put("Birth Date",birth);
                                         hashMap1.put("Confirm Password",confpassword);

                                         firebaseDatabase.getInstance().getReference("Customer")
                                                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                 .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {
                                                 mDialog.dismiss();
                                                 Intent i=new Intent(CustomerRegistration.this,CustomerLogin.class);
                                                 startActivity(i);
                                             }
                                         });

                                     }
                                 });
                             }else {
                                 mDialog.dismiss();
                                 //ReusableCodeForAll.ShowAlert(CustomerRegistration.this, "Error", task.getException().getMessage());
                             }

                         }
                     });

                 }

             }
         });

    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cpass.setErrorEnabled(false);
        cpass.setError("");
        Id.setErrorEnabled(false);
        Id.setError("");
        Location.setErrorEnabled(false);
        Location.setError("");
        BirthDay.setErrorEnabled(false);
        BirthDay.setError("");

        boolean isValid=false,isValidlocation=false,isValidlname=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false,isValidid=false,isValidbirth=false;
        if(TextUtils.isEmpty(fname)){
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(lname)){
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }else{
            isValidlname = true;
        }
        if(TextUtils.isEmpty(emailid)){
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        }else{
            if(password.length()<8){
                Pass.setErrorEnabled(true);
                Pass.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpassword)){
            cpass.setErrorEnabled(true);
            cpass.setError("Enter Password Again");
        }else{
            if(!password.equals(confpassword)){
                cpass.setErrorEnabled(true);
                cpass.setError("Password Dosen't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobile)){
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile Number Is Required");
        }else{
            if(mobile.length()<10){
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }
        if(TextUtils.isEmpty(id)){
            Id.setErrorEnabled(true);
            Id.setError("Area Is Required");
        }else{
            isValidid = true;
        }
        if(TextUtils.isEmpty(birth)){
            BirthDay.setErrorEnabled(true);
            BirthDay.setError("Please Enter Pincode");
        }else{
            isValidbirth = true;
        }
        if(TextUtils.isEmpty(location)){
            Location.setErrorEnabled(true);
            Location.setError("Fields Can't Be Empty");
        }else{
            isValidlocation = true;
        }
        isValid = (isValidlocation && isValidconfpassword && isValidpassword && isValidid && isValidemail && isValidmobilenum && isValidname && isValidbirth && isValidlname) ? true : false;
        return isValid;


    }
}
