package com.example.eventmaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerCustomAdapter extends RecyclerView.Adapter<RecyclerCustomAdapter.ViewHolder> {

    private final ArrayList<Events> data;
    private final LayoutInflater inflater;
    CustomItemClickListener customClickListener;

    //Generamos constructor nuevo, solo con data y le pasamos el contexto
    public RecyclerCustomAdapter(Context context, ArrayList<Events> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflamos la vista event
        View view = inflater.inflate(R.layout.event_recyclerview, parent, false);
        //Retornamos el ViewHolder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Igualamos los datos de la celda con los datos de la posición actual
        holder.eventTV.setText(data.get(position).getEvent());
        holder.siteTV.setText(data.get(position).getSite());
        holder.dateTV.setText(data.get(position).getDate());
        holder.timeTV.setText(data.get(position).getTime());
        holder.crossIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    data.remove(position);
                    notifyItemRemoved(position);
                    customClickListener.updateRemove();
                }
            }
        });
    }

    @Override
    //nos indica los item que va a pintar el recyclerview
    public int getItemCount() {
        return data.size();
    }

    //Creo una clase, dentro de la clase para generar la clase que va tener destinada la celda
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView eventTV;
        TextView siteTV;
        TextView dateTV;
        TextView timeTV;
        ImageView crossIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTV = itemView.findViewById(R.id.eventTV);
            eventTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customClickListener != null) {
                        customClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            siteTV = itemView.findViewById(R.id.siteTV);
            siteTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customClickListener != null) {
                        customClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            dateTV = itemView.findViewById(R.id.dateTV);
            dateTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customClickListener != null) {
                        customClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            timeTV = itemView.findViewById(R.id.timeTV);
            timeTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customClickListener != null) {
                        customClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            crossIV = itemView.findViewById(R.id.crossIV);
        }
    }

    void setCustomClickListener(CustomItemClickListener customClickListener) {
        this.customClickListener = customClickListener;
    }

    //Generamos la interfaz de datos y paso los metodos de posicionar los textos y de eliminar los eventos
    public interface CustomItemClickListener {
        void onItemClick(View view, int position);
        void updateRemove();
    }

}
