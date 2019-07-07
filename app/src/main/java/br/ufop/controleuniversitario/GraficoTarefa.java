package br.ufop.controleuniversitario;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GraficoTarefa  extends AppCompatActivity {
    private Intent it;
    private Bundle extra;
    private String user;
    private Tarefa tarefa;
    private ArrayList<Tarefa> arrayTarefa = new ArrayList<>();
    private BarChart barChart;

    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.grafico_disciplina);

        setTitle("Gráfico da distribuição de pontos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        it = getIntent();
        extra = it.getExtras();
        user = extra.getString("user");
        Log.e("GraficoTarefa", user);

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
                try{
                    barChart = (BarChart) findViewById(R.id.barchart);

                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    ArrayList<String> notas = new ArrayList<>();
                    int i = 0;
                    for(Tarefa tarefa : arrayTarefa){
                        barEntries.add(new BarEntry(i,Float.parseFloat(String.valueOf(tarefa.getNota()))));
                        notas.add(tarefa.getNomeTarefa());
                        i++;
                    }


                    BarDataSet barDataSet = new BarDataSet(barEntries, "Notas");
                    Log.e("barDataSet", barDataSet.toString());
//                BarDataSet barNotaSet = new BarDataSet(notas, "Materias");

                    BarData theData = new BarData(barDataSet);
//                theData.setBarWidth(10f);
                    barChart.setData(null);
                    barChart.setData(theData);
                    barChart.setTouchEnabled(true);
                    barChart.setScaleEnabled(true);
                }catch (java.lang.NullPointerException e){

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        Intent it = new Intent(GraficoTarefa.this, ListarTarefa.class);
        it.putExtra("user", user);
        finish();
        startActivity(it);
        super.onBackPressed();
    }
}