package br.ufop.controleuniversitario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.widgets.Snapshot;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.MissingFormatArgumentException;

public class Login extends Activity {
    private FirebaseDatabase database;

    private EditText etUser;
    private String user;

    private boolean validarUser;
    private boolean existeUser;
    @Override
    public void onCreate(Bundle bundle){

        super.onCreate(bundle);
        setContentView(R.layout.login);

    }

    public void irAluno(View view) {

        etUser = findViewById(R.id.etUser);

        validarUser = Util.isEmpty(etUser);

        if(!validarUser){
            Toast.makeText(this, "Usuário precisa ser preenchido", Toast.LENGTH_LONG).show();

        }else{
            user = etUser.getText().toString();
            database = FirebaseDatabase.getInstance();
            Intent it = new Intent(Login.this, MainActivity.class);
            it.putExtra("user", user);
            startActivity(it);

        }

    }
}
