package br.ufop.controleuniversitario;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class NovaDisciplina extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference mDatabase;

    private TimePickerDialog dialogTimePicker;

    private Intent it;
    private Bundle extra;
    private String user;

    private EditText etNomeDisciplina;
    private EditText etLimiteDeFaltas;
    private EditText etNumeroFaltasAtual;
    private EditText etMeta;
    private EditText etNotaAtual;

    private EditText etHorarioAula;
    private EditText etProfessor;
    private EditText etEmailProfessor;
    private ArrayList<Tarefa> tarefas;
    private Spinner spinnerDiaSemana;
    private Spinner spinnerSemestre;
    private Switch swAndamento;

    private String nomeDisciplina;
    private String semestre;
    private int limiteFaltas;
    private double meta;
    private String notaAtual;
    private String diaSemana;
    private String horarioAula;
    private String professor;
    private String emailProfessor;
    private String aluno;
    private String numeroFaltasAtual;

    private boolean andamento;
    private boolean validacaoNome;
    private boolean validacaoLimiteDeFaltas;
    private boolean validacaoMeta;
    private boolean validacaoNotaAtual;
    private boolean validacaoHorarioAula;
    private boolean validacaoProfessor;
    private boolean validacaoEmailProfessor;
    private boolean validacaoNumeroFaltasAtual;
    private DateFormat dateFormatTime;
    private Calendar calendar;




    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.nova_disciplina);
        setTitle("Adicionar Disciplina");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        spinnerSemestre =  findViewById(R.id.spinnerSemestre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_semestre, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemestre.setAdapter(adapter);
        etNomeDisciplina = findViewById(R.id.etNomeDisciplina);
        etLimiteDeFaltas = findViewById(R.id.etLimiteDeFaltas);
        etNumeroFaltasAtual = findViewById(R.id.etNumeroFaltaAtual);
        etMeta = findViewById(R.id.etMeta);
        etNotaAtual = findViewById(R.id.etNotaAtual);
        spinnerDiaSemana = findViewById(R.id.spinnerDiaSemana);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.array_dias_semana, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiaSemana.setAdapter(adapter2);
        etHorarioAula = findViewById(R.id.etHorarioAula);
        calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getDefault());
        dateFormatTime = DateFormat.getDateTimeInstance();
        etHorarioAula.setText(dateFormatTime.format(calendar.getTime()));
        dialogTimePicker = new TimePickerDialog(this,
            AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                etHorarioAula.setText(dateFormatTime.format(calendar.getTime()));
            }
        },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        etProfessor = findViewById(R.id.etProfessor);
        etEmailProfessor = findViewById(R.id.etEmailProfessor);
        swAndamento = findViewById(R.id.swAndamento);
        tarefas = new ArrayList<Tarefa>();
        it = getIntent();
        extra=it.getExtras();
        user = extra.getString("user");
        Log.e("NovaDisciplina", user);

    }

    public void cadastrarDisciplina(View view) {


        validacaoNome = (Util.isEmpty(etNomeDisciplina));
        validacaoLimiteDeFaltas = (Util.isEmpty(etLimiteDeFaltas));
        validacaoMeta = (Util.isEmpty(etMeta));
        validacaoNotaAtual = (Util.isEmpty(etNotaAtual));
        validacaoHorarioAula = (Util.isEmpty(etHorarioAula));
        validacaoProfessor = (Util.isEmpty(etProfessor));
        validacaoEmailProfessor = (Util.isEmpty(etEmailProfessor));
        validacaoNumeroFaltasAtual = (Util.isEmpty(etNumeroFaltasAtual));


        if (validacaoNome) {
            Toast.makeText(this, "O nome deve ser preenchido", Toast.LENGTH_LONG).show();
            etNomeDisciplina.setText("");
            etNomeDisciplina.requestFocus();

        } else if (validacaoLimiteDeFaltas) {
            Toast.makeText(this, "O número limite de faltas deve ser preenchido", Toast.LENGTH_LONG).show();
            etLimiteDeFaltas.setText("");
            etLimiteDeFaltas.requestFocus();

        } else if (validacaoMeta) {
            Toast.makeText(this, "A meta deve ser preenchida", Toast.LENGTH_LONG).show();
            etMeta.setText("");
            etMeta.requestFocus();
        } else  {

            nomeDisciplina = etNomeDisciplina.getText().toString();
            limiteFaltas = Integer.parseInt(etLimiteDeFaltas.getText().toString());
            meta = Float.parseFloat(etMeta.getText().toString());


            andamento = swAndamento.isChecked();
            semestre = spinnerSemestre.getSelectedItem().toString();
            diaSemana = spinnerDiaSemana.getSelectedItem().toString();
            Disciplina disciplinaAdd = new Disciplina(nomeDisciplina, semestre, limiteFaltas, meta, andamento, tarefas);



            if (!validacaoHorarioAula) {

                horarioAula = (etHorarioAula.getText().toString());
                disciplinaAdd.setHorarioAula(horarioAula);

            } else {

                disciplinaAdd.setHorarioAula(null);
            }

            if (!validacaoProfessor) {

                professor = etProfessor.getText().toString();
                disciplinaAdd.setProfessor(professor);

            } else {

                disciplinaAdd.setProfessor(null);
            }

            if (!validacaoEmailProfessor) {

                emailProfessor = (etEmailProfessor.getText().toString());
                disciplinaAdd.setEmailProfessor(emailProfessor);

            } else {

                disciplinaAdd.setEmailProfessor(null);

            }

            if (!validacaoNumeroFaltasAtual) {

                numeroFaltasAtual = (etNumeroFaltasAtual.getText().toString());
                disciplinaAdd.setNumeroFaltasAtual(Integer.parseInt(numeroFaltasAtual));

            } else {

                numeroFaltasAtual = String.valueOf(disciplinaAdd.getNumeroFaltasAtual());

            }

            if(!validacaoNotaAtual){

                notaAtual = (etNotaAtual.getText().toString());
                disciplinaAdd.setNotaAtual(Double.parseDouble(notaAtual));
            } else {
                notaAtual = String.valueOf(disciplinaAdd.getNotaAtual());
            }
            disciplinaAdd.setDiaSemana(diaSemana);
            aluno = extra.getString("user");

            escreverNovaDisciplina(disciplinaAdd, aluno);


            Toast.makeText(this, "Disciplina adicionada com sucesso! ", Toast.LENGTH_LONG).show();

            user = aluno;
            Intent it = new Intent(NovaDisciplina.this, ListarDisciplina.class);
            it.putExtra("user", user);
            startActivity(it);

        }

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
        Intent it = new Intent(NovaDisciplina.this, ListarDisciplina.class);
        startActivity(it);
        super.onBackPressed();
    }

    public void showTimePicker(View view) {
        dialogTimePicker.show();
    }

    private void escreverNovaDisciplina(Disciplina disciplina, String aluno) {
        Log.e( "Nova Disciplina",disciplina.toString());
        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
        raiz.child("Alunos/" + aluno + "/" + disciplina.getNomeDisciplina()).setValue(disciplina);

    }

}



