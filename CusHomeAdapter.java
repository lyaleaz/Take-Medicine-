package my.takeMedDesign.app.cusPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import my.takeMedDesign.app.R;
import my.takeMedDesign.app.UpdateMedModel;

public class CusHomeAdapter extends RecyclerView.Adapter<CusHomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UpdateMedModel> updateMedModelslist;
    DatabaseReference databaseReference;


    public CusHomeAdapter(Context context,ArrayList<UpdateMedModel>updateMedModelslist){

        this.updateMedModelslist = updateMedModelslist;
        this.context = context;

    }


    @NonNull
    @Override
    public CusHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_menumed,parent,false);
        return new CusHomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CusHomeAdapter.ViewHolder holder, int position) {

        //final UpdateMedModel updateMedModel = updateMedModelslist.get(position);
        holder.Medname.setText(updateMedModelslist.get(position).getMedicine());
        //updateMedModel.getRandomUID();
        holder.Price.setText(updateMedModelslist.get(position).getPrice());
        Picasso.with(context).load(updateMedModelslist.get(position).getImageURL()).into(holder.imageView);
        //holder.Medname.setText(updateMedModel.getQuantity());
        //updateMedModel.getPhId();
        //holder.Price.setText("Price:"+updateMedModel.getPrice());


    }

    @Override
    public int getItemCount() {
        return updateMedModelslist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView Medname,Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menu_image);
            Medname = itemView.findViewById(R.id.medname);
            Price = itemView.findViewById(R.id.medprice);
        }
    }
}