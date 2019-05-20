package br.ufop.controleuniversitario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NovaDisciplina extends Activity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText etNomeDisciplina;
    private EditText etSemestre;
    private EditText etLimiteDeFaltas;
    private EditText etNumeroFaltasAtual;
    private EditText etMeta;
    private EditText etDiaAula;
    private EditText etHorarioAula;
    private EditText etProfessor;
    private EditText etEmailProfessor;
    private ArrayList<Tarefa> tarefas;
    private Switch swAndamento;

    private String nomeDisciplina;
    private int semestre;
    private int limiteFaltas;
    private double meta;
    private String diaAula;
    private String horarioAula;
    private String professor;
    private String emailProfessor;
    private String aluno;
    private String numeroFaltasAtual;

    private boolean andamento;
    private boolean validacaoNome;
    private boolean validacaoSemestre;
    private boolean validacaoLimiteDeFaltas;
    private boolean validacaoMeta;
    private boolean validacaoHorarioAula;
    private boolean validacaoDiaAula;
    private boolean validacaoProfessor;
    private boolean validacaoEmailProfessor;
    private boolean validacaoNumeroFaltasAtual;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.nova_disciplina);
        etNomeDisciplina = findViewById(R.id.etNomeDisciplina);
        etSemestre = findViewById(R.id.etSemestre);
        etLimiteDeFaltas = findViewById(R.id.etLimiteDeFaltas);
        etNumeroFaltasAtual = findViewById(R.id.etNumeroFaltaAtual);
        etMeta = findViewById(R.id.etMeta);
        etDiaAula = findViewById(R.id.etDiaAula);
        etHorarioAula = findViewById(R.id.etHorarioAula);
        etProfessor = findViewById(R.id.etProfessor);
        etEmailProfessor = findViewById(R.id.etEmailProfessor);
        swAndamento = findViewById(R.id.swAndamento);
        tarefas = new ArrayList<Tarefa>();

    }

    public void cadastrarDisciplina(View view) {


        validacaoNome = (Util.isEmpty(etNomeDisciplina));
        validacaoSemestre = (Util.isEmpty(etSemestre));
        validacaoLimiteDeFaltas = (Util.isEmpty(etLimiteDeFaltas));
        validacaoMeta = (Util.isEmpty(etMeta));
        validacaoHorarioAula = (Util.isEmpty(etHorarioAula));
        validacaoDiaAula = (Util.isEmpty(etDiaAula));
        validacaoProfessor = (Util.isEmpty(etProfessor));
        validacaoEmailProfessor = (Util.isEmpty(etEmailProfessor));
        validacaoNumeroFaltasAtual = (Util.isEmpty(etNumeroFaltasAtual));


        if (validacaoNome) {
            Toast.makeText(this, "O nome deve ser preenchido", Toast.LENGTH_LONG).show();
            etNomeDisciplina.setText("");
            etNomeDisciplina.requestFocus();

        } else if (validacaoSemestre) {
            Toast.makeText(this, "O semestre deve ser preenchido", Toast.LENGTH_LONG).show();
            etSemestre.setText("");
            etSemestre.requestFocus();

        } else if (validacaoLimiteDeFaltas) {
            Toast.makeText(this, "O número limite de faltas deve ser preenchido", Toast.LENGTH_LONG).show();
            etLimiteDeFaltas.setText("");
            etLimiteDeFaltas.requestFocus();

        } else if (validacaoMeta) {
            Toast.makeText(this, "A meta deve ser preenchida", Toast.LENGTH_LONG).show();
            etMeta.setText("");
            etMeta.requestFocus();
        } else {

            nomeDisciplina = etNomeDisciplina.getText().toString();
            semestre = Integer.parseInt(etSemestre.getText().toString());
            limiteFaltas = Integer.parseInt(etLimiteDeFaltas.getText().toString());
            meta = Float.parseFloat(etMeta.getText().toString());


            if (swAndamento.isChecked()) {

                andamento = true;

            } else {

                andamento = false;

            }

            Disciplina disciplinaAdd = new Disciplina(nomeDisciplina, semestre, limiteFaltas, meta, andamento, tarefas);


            if (!validacaoDiaAula) {

                diaAula = etDiaAula.getText().toString();
                disciplinaAdd.setDiasDeAula(diaAula);

            } else {

                disciplinaAdd.setDiasDeAula(null);
            }

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


            aluno = "Matheus";
            Log.d("novaDisciplina", disciplinaAdd.toString());


//            final DatabaseReference alunoRef = database.getReference(aluno);
            final DatabaseReference aluno_disciplinaNomeRef = database.getReference(aluno + "/" + nomeDisciplina + "/nomeDisciplina");
            final DatabaseReference aluno_disciplinas_andamentoRef = database.getReference(aluno + "/" + nomeDisciplina + "/andamento");
            final DatabaseReference aluno_disciplinas_diasAulaRef = database.getReference(aluno + "/" + nomeDisciplina + "/diasAula");
            final DatabaseReference aluno_disciplinas_emailProfessorRef = database.getReference(aluno + "/" + nomeDisciplina + "/emailProfessor");
            final DatabaseReference aluno_disciplinas_horarioAulaRef = database.getReference(aluno + "/" + nomeDisciplina + "/horarioAula");
            final DatabaseReference aluno_disciplinas_limiteFaltaRef = database.getReference(aluno + "/" + nomeDisciplina + "/limiteFalta");
            final DatabaseReference aluno_disciplinas_metaRef = database.getReference(aluno + "/" + nomeDisciplina + "/meta");
            final DatabaseReference aluno_disciplinas_numeroFaltaAtualRef = database.getReference(aluno + "/" + nomeDisciplina + "/numeroFaltaAtual");
            final DatabaseReference aluno_disciplinas_professorRef = database.getReference(aluno + "/" + nomeDisciplina + "/professor");
            final DatabaseReference aluno_disciplinas_semestreRef = database.getReference(aluno + "/" + nomeDisciplina + "/semestre");

//            alunoRef.setValue(aluno);
            aluno_disciplinaNomeRef.setValue(nomeDisciplina);
            aluno_disciplinas_andamentoRef.setValue(andamento);
            aluno_disciplinas_diasAulaRef.setValue(diaAula);
            aluno_disciplinas_emailProfessorRef.setValue(emailProfessor);
            aluno_disciplinas_horarioAulaRef.setValue(horarioAula);
            aluno_disciplinas_limiteFaltaRef.setValue(limiteFaltas);
            aluno_disciplinas_metaRef.setValue(meta);
            aluno_disciplinas_numeroFaltaAtualRef.setValue(numeroFaltasAtual);
            aluno_disciplinas_professorRef.setValue(professor);
            aluno_disciplinas_semestreRef.setValue(semestre);
            Toast.makeText(this, "Disciplina adicionada com sucesso! ", Toast.LENGTH_LONG).show();

            etNomeDisciplina.setText("");
            etSemestre.setText("");
            etLimiteDeFaltas.setText("");
            etNumeroFaltasAtual.setText("");
            etMeta.setText("");
            etDiaAula.setText("");
            etHorarioAula.setText("");
            etProfessor.setText("");
            etEmailProfessor.setText("");
            swAndamento.setChecked(false);
            etNomeDisciplina.requestFocus();
        }


    }


}
