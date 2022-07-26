package my.takeMedDesign.app.phMedPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import my.takeMedDesign.app.MainMenu;
import my.takeMedDesign.app.R;
import my.takeMedDesign.app.UpdateMedModel;


public class PhHomeFragment extends Fragment {

    RecyclerView recyclerView;
    private List<UpdateMedModel> upadateMedModelList;
    private phHomeAdapter adapter;
    DatabaseReference dataa;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_ph_home,null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);
        recyclerView=v.findViewById(R.id.linear_layout);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        upadateMedModelList = new ArrayList<>();
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa = FirebaseDatabase.getInstance().getReference("User").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Pharm ph=snapshot.getValue(Pharm.class);
                PharmMed();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return v;
    }

    private void PharmMed() {

        String useremail=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MedDetails").child(useremail);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                upadateMedModelList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    UpdateMedModel updateDishModel = snapshot1.getValue(UpdateMedModel.class);
                    upadateMedModelList.add(updateDishModel);
                }


                adapter = new phHomeAdapter(getContext(),upadateMedModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.ph_bottom_navigation,menu);
    }
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idd = item.getItemId();
        if(idd == R.id.logout){
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/


    private void Logout() {
        //fire base
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
