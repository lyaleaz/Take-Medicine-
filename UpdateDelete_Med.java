package my.takeMedDesign.app.phMedPanel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.util.UUID;

import my.takeMedDesign.app.R;
import my.takeMedDesign.app.UpdateMedModel;

public class UpdateDelete_Med extends AppCompatActivity {
    TextInputLayout desc,qty,pri;
    TextView Medname;
    ImageButton  imageButton;
    Uri imageuri;
    String dburi;
    private Uri mCropimageuri;
    Button Update_med,Delete_med;
    String description,quantity,price,medicc,PhId;
    String RandomUID;
    String ID;
    StorageReference ref;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth FAuth;
    private ProgressDialog progressDialog;
    DatabaseReference dataa;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete__med);

        desc=(TextInputLayout)findViewById(R.id.description);
        qty=(TextInputLayout)findViewById(R.id.Quantity);
        pri= (TextInputLayout) findViewById(R.id.price);
        Medname= (TextView) findViewById(R.id.med_name);
        imageButton = (ImageButton) findViewById(R.id.image_upload);
        Update_med = (Button)findViewById(R.id.updatemed);
        Delete_med = (Button)findViewById(R.id.Deletemed);

        ID = getIntent().getStringExtra("updatedeletemed");


       final String useremail=FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa = firebaseDatabase.getInstance().getReference("User").child(useremail);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pharm pharm = snapshot.getValue(Pharm.class);
                Update_med.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        description = desc.getEditText().getText().toString().trim();
                        quantity = qty.getEditText().getText().toString().trim();
                        price = pri.getEditText().getText().toString().trim();
                        if (isValid()) {
                            if (imageuri != null) {
                                uploadImage();
                            } else {
                                updatemed(dburi);
                            }
                        }
                    }

                });
                Delete_med.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDelete_Med.this);
                        builder.setMessage("Are you sure you want to delet medicne");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseDatabase.getInstance().getReference("MedDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID).removeValue();
                                AlertDialog.Builder medd = new AlertDialog.Builder(UpdateDelete_Med.this);
                                medd.setMessage("Your Dish Has Been Deleted!");
                                medd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivities(new Intent(UpdateDelete_Med.this, phPanel_BottonNavigation.class));
                                    }
                                });
                                AlertDialog alert = medd.create();
                                alert.show();
                                ;
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                String useremail = FirebaseAuth.getInstance().getCurrentUser().getUid();
                progressDialog = new ProgressDialog(UpdateDelete_Med.this);
                databaseReference=FirebaseDatabase.getInstance().getReference("MedDetails")
                        .child(useremail).child(ID);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UpdateMedModel updateDishModel = snapshot.getValue(UpdateMedModel.class);
                        desc.getEditText().setText(updateDishModel.getDescription());
                        qty.getEditText().setText(updateDishModel.getQuantity());
                        Medname.setText("Dish name:"+updateDishModel.getMedicine());
                        medicc=updateDishModel.getMedicine();
                        pri.getEditText().setText(updateDishModel.getPrice());
                        dburi = updateDishModel.getImageURL();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FAuth = FirebaseAuth.getInstance();
                databaseReference=firebaseDatabase.getInstance().getReference("MedDetails");
                storage=FirebaseStorage.getInstance();
                storageReference= storage.getReference();
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void startActivities(Intent intent) {
    }

    private void updatemed(String buri) {
        PhId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MedDetails info = new MedDetails(medicc,quantity,price,description,buri,ID,PhId);
        firebaseDatabase.getInstance().getReference("MedDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(UpdateDelete_Med.this,"Dish Updates Successfully!",Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void uploadImage() {

        if(imageuri != null){

            progressDialog.setTitle("Uploading....");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updatemed(String.valueOf(uri));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateDelete_Med.this,"Failed:"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload "+(int) progress+"%");
                    progressDialog.setCanceledOnTouchOutside(false);
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
        if(TextUtils.isEmpty(description)){
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

}
