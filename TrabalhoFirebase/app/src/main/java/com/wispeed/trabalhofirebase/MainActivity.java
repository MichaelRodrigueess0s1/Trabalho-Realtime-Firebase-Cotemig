package com.wispeed.trabalhofirebase;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wispeed.trabalhofirebase.adapter.AdapterBanco;
import com.wispeed.trabalhofirebase.interfaces.RecyclerViewOnClickListenerHack;
import com.wispeed.trabalhofirebase.models.Banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  implements RecyclerViewOnClickListenerHack {
    private RecyclerView mRecyclerView;
    private ArrayList<Banco> mList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = new ArrayList<>();
        final  MainActivity ctx = this;
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_listabanco);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                AdapterBanco adapter = (AdapterBanco) mRecyclerView.getAdapter();
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);


        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
        final MainActivity autoReference = this;

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Banco banco = dataSnapshot1.getValue(Banco.class);
                    mList.add(banco);
                }


                AdapterBanco adapter = new AdapterBanco(ctx,  mList);
                adapter.setRecyclerViewOnClickListenerHack(ctx);
                mRecyclerView.setAdapter( adapter );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(this, mList.get(position).getNome(), Toast.LENGTH_LONG);
    }
}
