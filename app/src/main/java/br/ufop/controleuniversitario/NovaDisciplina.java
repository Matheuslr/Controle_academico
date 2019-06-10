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
import java.util.TimeZone;

public class NovaDisciplina extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private TimePickerDialog dialogTimePicker;

    private Intent it;
    private Bundle extra;

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
            Log.d("novaDisciplina", disciplinaAdd.toString());

            final DatabaseReference Alunos_aluno_disciplinaNomeRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/nomeDisciplina");
            final DatabaseReference Alunos_aluno_disciplinas_andamentoRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/andamento");
            final DatabaseReference Alunos_aluno_disciplinas_diasAulaRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/diasAula");
            final DatabaseReference Alunos_aluno_disciplinas_emailProfessorRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/emailProfessor");
            final DatabaseReference Alunos_aluno_disciplinas_horarioAulaRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/horarioAula");
            final DatabaseReference Alunos_aluno_disciplinas_limiteFaltaRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/limiteFalta");
            final DatabaseReference Alunos_aluno_disciplinas_metaRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/meta");
            final DatabaseReference Alunos_aluno_disciplinas_notaAtualRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/notaAtual");
            final DatabaseReference Alunos_aluno_disciplinas_numeroFaltaAtualRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/numeroFaltaAtual");
            final DatabaseReference Alunos_aluno_disciplinas_professorRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/professor");
            final DatabaseReference Alunos_aluno_disciplinas_semestreRef = database.getReference("Alunos/" +  aluno + "/" + nomeDisciplina + "/semestre");

            Alunos_aluno_disciplinaNomeRef.setValue(nomeDisciplina);
            Alunos_aluno_disciplinas_andamentoRef.setValue(andamento);
            Alunos_aluno_disciplinas_diasAulaRef.setValue(diaSemana);
            Alunos_aluno_disciplinas_emailProfessorRef.setValue(emailProfessor);
            Alunos_aluno_disciplinas_horarioAulaRef.setValue(horarioAula);
            Alunos_aluno_disciplinas_limiteFaltaRef.setValue(limiteFaltas);
            Alunos_aluno_disciplinas_metaRef.setValue(meta);
            Alunos_aluno_disciplinas_notaAtualRef.setValue(notaAtual);
            Alunos_aluno_disciplinas_numeroFaltaAtualRef.setValue(numeroFaltasAtual);
            Alunos_aluno_disciplinas_professorRef.setValue(professor);
            Alunos_aluno_disciplinas_semestreRef.setValue(semestre);
            Toast.makeText(this, "Disciplina adicionada com sucesso! ", Toast.LENGTH_LONG).show();

            etNomeDisciplina.setText("");
            spinnerSemestre.setTag(1);
            spinnerDiaSemana.setTag(1);
            etLimiteDeFaltas.setText("");
            etNumeroFaltasAtual.setText("");
            etMeta.setText("");
            etNotaAtual.setText("");
            etHorarioAula.setText("");
            etProfessor.setText("");
            etEmailProfessor.setText("");
            swAndamento.setChecked(false);
            etNomeDisciplina.requestFocus();
        }

    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    public void showTimePicker(View view) {
        dialogTimePicker.show();
    }
}



