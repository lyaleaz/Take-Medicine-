
package my.takeMedDesign.app.cusPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import my.takeMedDesign.app.R;
import my.takeMedDesign.app.UpdateMedModel;

public class cus_homeFragmnet extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    private CusHomeAdapter adapter;
    private ArrayList<UpdateMedModel> list;
    SwipeRefreshLayout swipeRefreshLayout;
    DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ph_home, null);
        getActivity().setTitle("Home");
        recyclerView = v.findViewById(R.id.recycle_menu);
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayouty);
        swipeRefreshLayout.setOnRefreshListener(this);
        list = new ArrayList<UpdateMedModel>();

//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//                DatabaseReference ref = database.child("MedDetails");
//                String  userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
////                dataa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
////                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        customermenu();
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError error) {
////
////                    }
////                });
//            }
//        });


        return v;
    }

    //
    @Override
    public void onRefresh() {
        customermenu();
    }
    //
    private void customermenu() {
        ref = FirebaseDatabase.getInstance().getReference().child("MedDetails");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UpdateMedModel Med = dataSnapshot.getValue(UpdateMedModel.class);
                    list.add(Med);
                }
                adapter = new CusHomeAdapter(getContext(), list);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }


        });


    }
}
