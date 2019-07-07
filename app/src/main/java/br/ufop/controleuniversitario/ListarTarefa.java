package br.ufop.controleuniversitario;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ListarTarefa extends AppCompatActivity {
    private DatePickerDialog dialogDatePicker;
    private AdapterTarefa adapter;
    private ListView lvListarTarefa;

    //Intent vars
    private Intent it;
    private Bundle extra;
    private String user;

    private static boolean alreadyRecreated = false;
    //Disciplina
    private Tarefa tarefa;
    private ArrayList<Tarefa> arrayTarefa = new ArrayList<>();
    private ArrayList<Tarefa> arrayAdapterTarefa = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setTitle("Lista de tarefas");
        setContentView(R.layout.listar_tarefa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(!alreadyRecreated){
            recreate();
            alreadyRecreated = true;
        }
        it = getIntent();
        extra = it.getExtras();
        user = extra.getString("user");
        Log.e("ListarTarefa", user);


        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
        tarefa = new Tarefa();
        raiz.child("Alunos/"+user+"/tarefas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    tarefa = snapshot.getValue(Tarefa.class);
                    arrayTarefa.add(tarefa);
                    Log.e("Tarefa", tarefa.toString());
                }
                Collections.sort(arrayTarefa, new Comparator<Tarefa>() {
                    public int compare(Tarefa o1, Tarefa o2) {
                        Date date1, date2;
                        try {
                            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(o1.getDataEntrega());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            date1 = null;
                        }
                        try {
                            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(o2.getDataEntrega());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            date2 = null;
                        }
                        if (date1 == null || date2 == null)
                            return 0;
                        return date1.compareTo(date2);
                    }
                });

                adapter = new AdapterTarefa(getApplicationContext(), arrayAdapterTarefa);
                lvListarTarefa = findViewById(R.id.lvListarTarefa);
                lvListarTarefa.setAdapter(null);
                lvListarTarefa.setAdapter(adapter);

                if(arrayAdapterTarefa.size() == 0){
                    for(Integer i = 0; i < arrayTarefa.size(); i++) {
                        tarefa = arrayTarefa.get(i);
                        adapter.add(tarefa);
                    }
                } else {
                    arrayAdapterTarefa.clear();
                    for(Integer i = 0; i < arrayTarefa.size(); i++) {
                        tarefa = arrayTarefa.get(i);
                        adapter.add(tarefa);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lvListarTarefa = findViewById(R.id.lvListarTarefa);
        lvListarTarefa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                it = getIntent();
                extra = it.getExtras();
                user = extra.getString("user");
                Intent it = new Intent(ListarTarefa.this, EditarTarefa.class);
                it.putExtra("user", user+"/tarefas/"+arrayTarefa.get(position).getNomeTarefa());
                finish();
                startActivity(it);
            }
        });

    }

    public void adicionarTarefa(View view) {
        Intent it = new Intent(ListarTarefa.this, AdicionarTarefa.class);
        it.putExtra("user", user);
        finish();
        startActivity(it);
    }

    public void irGrafico(View view) {
        Intent it = new Intent(ListarTarefa.this, GraficoTarefa.class);
        it.putExtra("user", user);
        finish();
        startActivity(it);

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
        String user_nomeDisciplina = extra.getString("user");
        String [] parts = user_nomeDisciplina.split("/");
        user = parts[0];
        Intent it = new Intent(ListarTarefa.this, ListarDisciplina.class);
        it.putExtra("user", user);
        finish();
        startActivity(it);
        super.onBackPressed();
    }
}
