package br.ufop.controleuniversitario;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GraficoDisciplina extends AppCompatActivity {
    private Intent it;
    private Bundle extra;
    private String user;
    private ArrayList<Disciplina> arrayDisciplina = new ArrayList<Disciplina>();
    private Disciplina disciplina;
    private BarChart barChart;

    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.grafico_disciplina);

        setTitle("Gráfico de nota das disciplinas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        it = getIntent();
        extra = it.getExtras();
        user = extra.getString("user");
        Log.e("GraficoDisciplina", user);

        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
        disciplina = new Disciplina();
        raiz.child("Alunos/" + user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    disciplina = snapshot.getValue(Disciplina.class);
                    arrayDisciplina.add(disciplina);

                }

                barChart = (BarChart) findViewById(R.id.barchart);

                ArrayList<BarEntry> barEntries = new ArrayList<>();
                ArrayList<String> notas = new ArrayList<>();
                int i = 0;
                for(Disciplina disciplina : arrayDisciplina){
                    barEntries.add(new BarEntry(i,Float.parseFloat(String.valueOf(disciplina.getNotaAtual()))));
                    notas.add(disciplina.getNomeDisciplina());
                    i++;
                }
                try{
                    for(i = 0; i < barEntries.size(); i++){
                        Log.e("Disciplina", barEntries.get(i).toString());
                    }
                } catch (java.lang.NullPointerException e){
                    Log.e("se", "fudeu");
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
        user = extra.getString("user");
        Intent it = new Intent(GraficoDisciplina.this, ListarDisciplina.class);
        it.putExtra("user", user);
        startActivity(it);
        finish();
    }
}
