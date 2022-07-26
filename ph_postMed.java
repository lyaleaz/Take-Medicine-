

package my.takeMedDesign.app.phMedPanel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import my.takeMedDesign.app.R;

public class ph_postMed extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =  71;
    ImageButton imageButton;
    Button post_med;
    TextInputLayout Medicine;
    TextInputLayout desc,qty,pri;
    String descrption,quantity,price,medicine;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,dataa;
    FirebaseAuth Fauth;
    String RandomUID,PharmId;
    String link = "" ;
    private Uri filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph_post_med);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        Medicine=(TextInputLayout)findViewById(R.id.name);
        desc=(TextInputLayout)findViewById(R.id.description);
        qty=(TextInputLayout)findViewById(R.id.Quantity);
        pri=(TextInputLayout)findViewById(R.id.price);
        post_med=(Button)findViewById(R.id.post2);
        Fauth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getInstance().getReference("MedDetails");
        try {
            PharmId=FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataa = firebaseDatabase.getInstance().getReference();
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imageButton = (ImageButton) findViewById(R.id.image_upload);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public  void onClick(View v)
                        {
                            chooseImage();
                        }
                    });
                    post_med.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            medicine = Medicine.getEditText().getText().toString().trim();
                            descrption = desc.getEditText().getText().toString().trim();
                            quantity = qty.getEditText().getText().toString().trim();
                            price = pri.getEditText().getText().toString().trim();
                            if(isValid())
                            {
                                uploadImage();
                                Log.d("link=",link);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                MedDetails info = new MedDetails(medicine,quantity,price,descrption,String.valueOf(filepath),RandomUID,PharmId);
                                firebaseDatabase.getInstance().getReference("MedDetails").child(medicine)
                                        .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ph_postMed.this,"Med Posted Successfully!",Toast.LENGTH_SHORT).show();
                                        Intent i=new Intent(ph_postMed.this,phPanel_BottonNavigation.class);
                                        startActivity(i);
                                    }
                                });
                            }


                        }
                    });
                }
                @Override
                public void onCancelled (@NonNull DatabaseError error){


                }

            });
        }
        catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }


    }


    private  void uploadImage()
    {
        FirebaseStorage storage;
        StorageReference storageReference;


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(filepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            link = "images/"+ UUID.randomUUID().toString() ;
            StorageReference ref = storageReference.child(link);
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ph_postMed.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ph_postMed.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    private  boolean isValid()
    {
        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");
        boolean isValidDescription=false,isValidPrice=false,isValidQuantity=false,isValid=false;
        if(TextUtils.isEmpty(descrption)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Rwquired");
        }
        else{
            desc.setError(null);
            isValidDescription=true;
        }
        if(TextUtils.isEmpty(quantity)){
            qty.setErrorEnabled(true);
            qty.setError("Enter number of Plates or Items");
        }else{
            isValidQuantity=true;
        }
        if(TextUtils.isEmpty(price)){
            pri.setErrorEnabled(true);
            pri.setError("Please Mention Price");
        }else{
            isValidPrice=true;
        }
        isValid = (isValidDescription && isValidQuantity && isValidPrice)?true:false;
        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageButton.setImageBitmap(bitmap);


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }

}

