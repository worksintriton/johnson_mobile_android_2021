package com.triton.johnson_tap_app;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.activity.Daily_Collection_DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<ModelRecycler> dataModelArrayList;
    private Context mcon;

    public RetrofitAdapter(Context ctx, ArrayList<ModelRecycler> dataModelArrayList){
        mcon = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    public void filterList(ArrayList<ModelRecycler> filterllist)
    {
        dataModelArrayList = filterllist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.pop_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView_pop.setText(dataModelArrayList.get(position).getUrtno());
        holder.textView1_pop.setText(dataModelArrayList.get(position).getBank_details());
        holder.textView2_pop.setText(dataModelArrayList.get(position).getAmount());
        holder.textView3_pop.setText(dataModelArrayList.get(position).getCustomer_name());
        holder.textView4_pop.setText(dataModelArrayList.get(position).getIfsc_code());
        holder.textView5_pop.setText(dataModelArrayList.get(position).getBalance_amt());

        holder.textView_pop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                holder.lin.setBackgroundColor(Color.BLUE);

                Intent intent = new Intent(mcon,Daily_Collection_DetailsActivity.class);
                intent.putExtra("urt_no",dataModelArrayList.get(position).getUrtno());
                intent.putExtra("bank_details",dataModelArrayList.get(position).getBank_details());
                intent.putExtra("amt",dataModelArrayList.get(position).getAmount());
                intent.putExtra("customer_name",dataModelArrayList.get(position).getCustomer_name());
                intent.putExtra("ifsc_code",dataModelArrayList.get(position).getIfsc_code());
                intent.putExtra("balance_amt",dataModelArrayList.get(position).getBalance_amt());
                intent.putExtra("Radio_button","RTGS");
                mcon.startActivity(intent);
            }
        });
        holder.textView1_pop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                holder.lin.setBackgroundColor(Color.BLUE);

                Intent intent = new Intent(mcon,Daily_Collection_DetailsActivity.class);
                intent.putExtra("urt_no",dataModelArrayList.get(position).getUrtno());
                intent.putExtra("bank_details",dataModelArrayList.get(position).getBank_details());
                intent.putExtra("amt",dataModelArrayList.get(position).getAmount());
                intent.putExtra("customer_name",dataModelArrayList.get(position).getCustomer_name());
                intent.putExtra("ifsc_code",dataModelArrayList.get(position).getIfsc_code());
                intent.putExtra("balance_amt",dataModelArrayList.get(position).getBalance_amt());
                intent.putExtra("Radio_button","RTGS");
                mcon.startActivity(intent);
            }
        });
        holder.textView2_pop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                holder.lin.setBackgroundColor(Color.BLUE);

                Intent intent = new Intent(mcon,Daily_Collection_DetailsActivity.class);
                intent.putExtra("urt_no",dataModelArrayList.get(position).getUrtno());
                intent.putExtra("bank_details",dataModelArrayList.get(position).getBank_details());
                intent.putExtra("amt",dataModelArrayList.get(position).getAmount());
                intent.putExtra("customer_name",dataModelArrayList.get(position).getCustomer_name());
                intent.putExtra("ifsc_code",dataModelArrayList.get(position).getIfsc_code());
                intent.putExtra("balance_amt",dataModelArrayList.get(position).getBalance_amt());
                intent.putExtra("Radio_button","RTGS");
                mcon.startActivity(intent);
            }
        });
        holder.textView3_pop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                holder.lin.setBackgroundColor(Color.BLUE);

                Intent intent = new Intent(mcon,Daily_Collection_DetailsActivity.class);
                intent.putExtra("urt_no",dataModelArrayList.get(position).getUrtno());
                intent.putExtra("bank_details",dataModelArrayList.get(position).getBank_details());
                intent.putExtra("amt",dataModelArrayList.get(position).getAmount());
                intent.putExtra("customer_name",dataModelArrayList.get(position).getCustomer_name());
                intent.putExtra("ifsc_code",dataModelArrayList.get(position).getIfsc_code());
                intent.putExtra("balance_amt",dataModelArrayList.get(position).getBalance_amt());
                intent.putExtra("Radio_button","RTGS");
                mcon.startActivity(intent);
            }
        });
        holder.textView4_pop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                holder.lin.setBackgroundColor(Color.BLUE);

                Intent intent = new Intent(mcon,Daily_Collection_DetailsActivity.class);
                intent.putExtra("urt_no",dataModelArrayList.get(position).getUrtno());
                intent.putExtra("bank_details",dataModelArrayList.get(position).getBank_details());
                intent.putExtra("amt",dataModelArrayList.get(position).getAmount());
                intent.putExtra("customer_name",dataModelArrayList.get(position).getCustomer_name());
                intent.putExtra("ifsc_code",dataModelArrayList.get(position).getIfsc_code());
                intent.putExtra("balance_amt",dataModelArrayList.get(position).getBalance_amt());
                intent.putExtra("Radio_button","RTGS");
                mcon.startActivity(intent);
            }
        });
        holder.textView5_pop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                holder.lin.setBackgroundColor(Color.BLUE);

                Intent intent = new Intent(mcon,Daily_Collection_DetailsActivity.class);
                intent.putExtra("urt_no",dataModelArrayList.get(position).getUrtno());
                intent.putExtra("bank_details",dataModelArrayList.get(position).getBank_details());
                intent.putExtra("amt",dataModelArrayList.get(position).getAmount());
                intent.putExtra("customer_name",dataModelArrayList.get(position).getCustomer_name());
                intent.putExtra("ifsc_code",dataModelArrayList.get(position).getIfsc_code());
                intent.putExtra("balance_amt",dataModelArrayList.get(position).getBalance_amt());
                intent.putExtra("Radio_button","RTGS");
                mcon.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView_pop, textView1_pop, textView2_pop, textView3_pop, textView4_pop, textView5_pop;
        ImageView iv;
        LinearLayout lin;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView_pop = (TextView) itemView.findViewById(R.id.textView_pop);
            textView1_pop = (TextView) itemView.findViewById(R.id.textView1_pop);
            textView2_pop = (TextView) itemView.findViewById(R.id.textView2_pop);
            textView3_pop = (TextView) itemView.findViewById(R.id.textView3_pop);
            textView4_pop = (TextView) itemView.findViewById(R.id.textView4_pop);
            textView5_pop = (TextView) itemView.findViewById(R.id.textView5_pop);
            lin = (LinearLayout) itemView.findViewById(R.id.lin);
        }

    }
}
