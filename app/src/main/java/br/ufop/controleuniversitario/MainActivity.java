package br.ufop.controleuniversitario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String user;
    private Bundle extra;
    private Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        it = getIntent();
        extra = it.getExtras();
        user = extra.getString("user");
        Log.e("MainActivity", user);


    }



    public void adicionarDisciplina(View view) {


        Intent it = new Intent(MainActivity.this, NovaDisciplina.class);
        it.putExtra("user", user);
        startActivity(it);

    }

    public void editarDisciplina(View view) {
        user = extra.getString("user");
        Intent it = new Intent(MainActivity.this, ListarDisciplina.class);
        it.putExtra("user", user);
        startActivity(it);
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();

    }

}
