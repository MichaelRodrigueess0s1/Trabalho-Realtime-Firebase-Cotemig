package com.wispeed.trabalhofirebase.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wispeed.trabalhofirebase.R;
import com.wispeed.trabalhofirebase.interfaces.RecyclerViewOnClickListenerHack;
import com.wispeed.trabalhofirebase.models.Agencia;
import com.wispeed.trabalhofirebase.models.Banco;

import java.util.List;

public class AdapterBanco extends RecyclerView.Adapter<AdapterBanco.MyViewHolder> {
    private List<Banco> mList;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;


    public AdapterBanco(Context c, List<Banco> l){
        mList = l;
        context  = c;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = mLayoutInflater.inflate(R.layout.item_banco, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Log.i("LOG", "onBindViewHolder()");
        //myViewHolder.ivCar.setImageResource( mList.get(position).getFoto() );
        myViewHolder.nome.setText(mList.get(position).getNome() );
        myViewHolder.cnpj.setText( mList.get(position).getCnpj() );

        String agencias = "";
        for(Agencia ag : mList.get(position).getAgencias())
        {
            agencias += " Numero: "+ ag.getNumero() + " Endereço:  " + ag.getEndereco() + " .";
        }
        myViewHolder.agencias.setText( agencias);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(Banco c, int position){
        mList.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nome;
        public TextView cnpj;
        public TextView agencias;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = (TextView) itemView.findViewById(R.id.tv_nome);
            cnpj = (TextView) itemView.findViewById(R.id.tv_cnpj);
            agencias = (TextView) itemView.findViewById(R.id.tv_agencia);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                Banco e = mList.get(getPosition());
                mRecyclerViewOnClickListenerHack.onClickListener(v, e.getCodigo());
            }
        }
    }
}