package app.struct.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.struct.R;
import app.struct.models.MarkerModel;

/**
 * Created by arwin on 8/18/17.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>  {

    private final Context mContext;
    List<MarkerModel> restaurantList;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtTitle2, txtTitle3;
        CardView cardView;
        MyViewHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.title);
            txtTitle2 = (TextView) view.findViewById(R.id.genre);
            cardView = (CardView) view.findViewById(R.id.cardView);
            //txtTitle3 = (TextView) view.findViewById(R.id.genre);
        }
    }

    public RestaurantAdapter(Context mContext, List<MarkerModel> restaurantList) {
        this.restaurantList = restaurantList;
        this.mContext = mContext;
    }

    @Override
    public RestaurantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card, parent, false);

        return new RestaurantAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MarkerModel order = restaurantList.get(position);

        String companyName = order.getCompanyName();
        //String description = order.getDescription();
        final String latitude = order.getLatitude();
        final String longitude = order.getLongitude();
        final String hourType = order.getHourType();


        holder.txtTitle.setText(companyName);
        holder.txtTitle2.setText(hourType);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(mContext, Maps.class);
//                intent.putExtra("long", longitude);
//                intent.putExtra("lat", latitude);
//                mContext.startActivity(intent);
            }
        });

    }

    private void showAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Are you sure you want to delete this address ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                //deleteModule();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void clear(){
        restaurantList.clear();
        notifyDataSetChanged();
    }
}
