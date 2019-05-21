package br.ufop.controleuniversitario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String user;
    private Bundle extra;
    private Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        it = getIntent();
        extra = it.getExtras();


    }

    public void adicionarDisciplina(View view) {

        user = extra.getString("user");
        Intent it = new Intent(MainActivity.this, NovaDisciplina.class);
        it.putExtra("user", user);
        startActivity(it);

    }
}
