package com.aiep.simologia_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aiep.simologia_app.R;
import com.aiep.simologia_app.model.Sismos;

import java.util.List;

public class SismosAdapter extends RecyclerView.Adapter<SismosAdapter.ViewHolder> {

    private List<Sismos> sismos;
    private Context context;

    public SismosAdapter(List<Sismos> sismos, Context context) {
        this.sismos = sismos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sismo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sismos currentSismo = sismos.get(position);
        holder.fechaTxt.setText(currentSismo.getFecha());

        holder.fechaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.toggleInfoVisibility();
            }
        });

        holder.profundidadTxt.setText("Profundidad: " + currentSismo.getProfundidad());
        holder.magnitudTxt.setText("Magnitud: " + currentSismo.getMagnitud());
        holder.ubicacionTxt.setText("Ubicación: " + currentSismo.getRefGeografica());


        holder.hideInfo();
    }

    @Override
    public int getItemCount() {
        return sismos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fechaTxt;
        private TextView profundidadTxt;
        private TextView magnitudTxt;
        private TextView ubicacionTxt;
        private LinearLayout infoLayout; // Agrega un LinearLayout para envolver las secciones de información

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fechaTxt = itemView.findViewById(R.id.fechaTxt);
            profundidadTxt = itemView.findViewById(R.id.profundidadTxt);
            magnitudTxt = itemView.findViewById(R.id.magnitudTxt);
            ubicacionTxt = itemView.findViewById(R.id.ubicacionTxt);
            infoLayout = itemView.findViewById(R.id.infoLayout); // Asigna el LinearLayout
        }

        public void toggleInfoVisibility() {
            int visibility = infoLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            infoLayout.setVisibility(visibility);
        }

        public void hideInfo() {
            infoLayout.setVisibility(View.GONE);
        }
    }
}
