package my.takeMedDesign.app.phMedPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import my.takeMedDesign.app.R;

public class PhProfileFregment extends Fragment {

    Button postMed;
    ConstraintLayout backimg;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_ph_prifile,null);
        getActivity().setTitle("Post A new Medecine");

       postMed=(Button)v.findViewById(R.id.postMed);
       postMed.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getContext(),ph_postMed.class));
           }
       });

        return v;
    }
}
