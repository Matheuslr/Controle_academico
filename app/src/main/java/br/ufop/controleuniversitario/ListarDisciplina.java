package br.ufop.controleuniversitario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarDisciplina extends AppCompatActivity {

    private AdapterDisciplina adapter;
    private ListView lvListarDisciplina;

    //Intent vars
    private Intent it;
    private Bundle extra;
    private String user;

    private static boolean alreadyRecreated = false;
    //Disciplina
    private ArrayList<Disciplina> arrayDisciplina = new ArrayList<Disciplina>();
    private Disciplina disciplina;
    ArrayList<Disciplina> arrayAdapterDisciplina = new ArrayList<>();
    @Override
    public void onCreate(Bundle bundle){

        super.onCreate(bundle);
        setContentView(R.layout.listar_disciplina);
        if(!alreadyRecreated){
            recreate();
            alreadyRecreated = true;
        }

        setTitle("Lista de disciplinas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        it = getIntent();
        extra = it.getExtras();
        user = extra.getString("user");
        Log.e("ListarDisciplina", user);


        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
        disciplina = new Disciplina();
        raiz.child("Alunos/" + user).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    disciplina = snapshot.getValue(Disciplina.class);
                    arrayDisciplina.add(disciplina);
                    Log.e("Disciplina", disciplina.toString());
                }


                adapter = new AdapterDisciplina(getApplicationContext(), arrayAdapterDisciplina);
                lvListarDisciplina = findViewById(R.id.lvListarDisciplina);
                lvListarDisciplina.setAdapter(null);
                lvListarDisciplina.setAdapter(adapter);
                disciplina = new Disciplina();
                if(arrayAdapterDisciplina.size() == 0){
                    for(Integer i = 0; i < arrayDisciplina.size(); i++){
                        disciplina = arrayDisciplina.get(i);
                        adapter.add(disciplina);
                    }
                } else {
                    arrayAdapterDisciplina.clear();
                    for(Integer i = 0; i < arrayDisciplina.size(); i++){
                        disciplina = arrayDisciplina.get(i);
                        adapter.add(disciplina);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_LONG).show();
            }
        });
        lvListarDisciplina = findViewById(R.id.lvListarDisciplina);
        lvListarDisciplina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it = getIntent();
                extra = it.getExtras();
                user = extra.getString("user");
                Intent it = new Intent(ListarDisciplina.this, EditarDisciplina.class);
                it.putExtra("user", user+"/"+arrayDisciplina.get(position).getNomeDisciplina());
                finish();
                startActivity(it);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();

    }
    @Override
    public void onBackPressed(){
        it = getIntent();
        extra = it.getExtras();
        user = extra.getString("user");
        Intent intent = new Intent();
        intent.putExtra("user", user);
        finish();
        super.onBackPressed();
    }

    public void adicionarDisciplina(View view) {
        Intent it = new Intent(ListarDisciplina.this, AdicionarDisciplina.class);
        it.putExtra("user", user);
        finish();
        startActivity(it);

    }
}