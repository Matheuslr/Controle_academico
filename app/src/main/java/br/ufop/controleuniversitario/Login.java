package br.ufop.controleuniversitario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.MissingFormatArgumentException;

public class Login extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;

    private EditText etUser;
    private String user;

    private boolean validarUser;
    private boolean existeUser;
    @Override
    public void onCreate(Bundle bundle){

        super.onCreate(bundle);
        setContentView(R.layout.login);
        setTitle("Login");


    }

    public void irAluno(View view) {

        etUser = findViewById(R.id.etUser);

        validarUser = Util.isEmpty(etUser);

        if(!validarUser){
            user = etUser.getText().toString();
            Log.e("Login", user);
            Intent it = new Intent(Login.this, ListarDisciplina.class);
            it.putExtra("user", user);
            startActivity(it);

        }else{

            Toast.makeText(this, "Usuário precisa ser preenchido", Toast.LENGTH_LONG).show();

        }

    }




}
