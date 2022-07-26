package my.takeMedDesign.app.phMedPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.takeMedDesign.app.R;
import my.takeMedDesign.app.UpdateMedModel;

public class phHomeAdapter extends RecyclerView.Adapter<phHomeAdapter.ViewHolder> {

    private Context mcont;
    private List<UpdateMedModel>updateMedModelList;

    public phHomeAdapter(Context context , List<UpdateMedModel>updateMedModelList){
        this.updateMedModelList = updateMedModelList;
        this.mcont = context;
    }

    @NonNull
    @Override
    public phHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcont).inflate(R.layout.phmenu_update_delete,parent,false);
        return new phHomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull phHomeAdapter.ViewHolder holder, int position) {
        final UpdateMedModel updateMedModel = updateMedModelList.get(position);
        holder.meds.setText(updateMedModel.getMedicine());
        updateMedModel.getRandomUID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(mcont,UpdateDelete_Med.class);
                intent.putExtra("uptadedeletemed",updateMedModel.getRandomUID());
                mcont.startActivities(new Intent[]{intent});
            }
        });


    }

    @Override
    public int getItemCount() {
        return updateMedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView meds;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meds = itemView.findViewById(R.id.med_name);

        }
    }
}
