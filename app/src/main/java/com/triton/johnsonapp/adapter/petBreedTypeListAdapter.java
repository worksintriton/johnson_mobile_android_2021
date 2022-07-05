package com.triton.johnsonapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnsonapp.Forms.RowBasedInputFormActivity;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.GroupListActivity;
import com.triton.johnsonapp.activitybased.ActivityJobListActivity;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.interfaces.PetBreedTypeSelectListener;
import com.triton.johnsonapp.requestpojo.BreedTypeRequest1;
import com.triton.johnsonapp.responsepojo.BreedTypeResponse1;
import com.triton.johnsonapp.responsepojo.FormDataStoreResponse;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class petBreedTypeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "PetBreedTypesListAdapter";
    private Context context;
    BreedTypeResponse1.DataBean currentItem;
    private List<BreedTypeResponse1.DataBean> breedTypedataBeanList;
    PetBreedTypeSelectListener petBreedTypeSelectListener;

    public petBreedTypeListAdapter(Context context, List<BreedTypeResponse1.DataBean> breedTypedataBeanList, PetBreedTypeSelectListener petBreedTypeSelectListener) {
        this.context = context;
        this.breedTypedataBeanList = breedTypedataBeanList;
        this.petBreedTypeSelectListener = petBreedTypeSelectListener;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_breedttype_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("LogNotTimber")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = breedTypedataBeanList.get(position);

        if (currentItem.getName() != null) {
            //holder.spinner(currentItem.getEMPNAME() + "," + currentItem.getEMPNO());

        }


        holder.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breedTypedataBeanList.get(position) != null) {

                    breedTypedataBeanList.get(position).getName();
                    //breedTypedataBeanList.get(position).getMOBILE();

                  //  String mobile = breedTypedataBeanList.get(position).getMOBILE();
                   // Log.e("mobile", mobile);
                   // String s = breedTypedataBeanList.get(position).getEMPNAME() + "," + breedTypedataBeanList.get(position).getEMPNO();
                   // Log.e("s", s);




                //    Toast.makeText(context, "value" + s, Toast.LENGTH_LONG).show();

                    SharedPreferences sharedPref = context.getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    //editor.putString("value", s);
                    editor.apply();

                    SharedPreferences sharedpre = context.getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor edior = sharedpre.edit();
                    //edior.putString("mobile", mobile);
                    edior.apply();
                }


            }
        });

    }


    public int getItemCount() {
        return breedTypedataBeanList.size();
    }

 /*   public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    breedTypedataBeanListFiltered = breedTypedataBeanList;
                } else {
                    List<BreedTypeResponse.DataBean> filteredList = new ArrayList<>();
                    for (BreedTypeResponse.DataBean row : breedTypedataBeanList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPet_breed().toLowerCase().contains(charString.toLowerCase()) || row.getPet_breed().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    breedTypedataBeanListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = breedTypedataBeanListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                breedTypedataBeanListFiltered = (ArrayList<BreedTypeResponse.DataBean>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }*/

    public void filterList(List<BreedTypeResponse1.DataBean> breedTypedataBeanListFiltered) {
        breedTypedataBeanList = breedTypedataBeanListFiltered;
        Log.w(TAG, "breedTypedataBeanList : " + new Gson().toJson(breedTypedataBeanList));

        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public Spinner spinner;
        public LinearLayout ll_root;


        public ViewHolderOne(View itemView) {
            super(itemView);

            spinner = itemView.findViewById(R.id.spr_dropdown);
            ll_root = itemView.findViewById(R.id.ll_root);


        }


    }


}
