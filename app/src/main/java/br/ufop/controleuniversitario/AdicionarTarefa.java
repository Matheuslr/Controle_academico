package br.ufop.controleuniversitario;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class AdicionarTarefa extends AppCompatActivity {

    private DatePickerDialog dialogDatePicker;
    private TimePickerDialog dialogTimePicker;
    private DateFormat dateFormatTime;
    private DateFormat dateFormat;
    private AdapterDisciplina adapter;

    //Intent vars
    private Intent it;
    private Bundle extra;
    private String user;
    private String nomeUser;
    private String nomeDisciplina;

    private static boolean alreadyRecreated = false;
    //Disciplina
    private ArrayList<Disciplina> arrayDisciplina = new ArrayList<Disciplina>();
    private Disciplina disciplina;
    private Tarefa tarefa;
    ArrayList<Disciplina> arrayAdapterDisciplina = new ArrayList<>();

    private EditText etNomeDisciplina;
    private EditText etDescricao;
    private EditText etValor;
    private EditText etNomeTarefa;
    private EditText etNotaAlcancada;
    private EditText etMeta;
    private EditText etDataEntrega;
    private EditText etHoraEntrega;

    private String descricao;
    private String nomeTarefa;
    private Double valor;
    private Double notaAlcancada;
    private Double meta;
    private String dataEntrega;
    private String horaEntrega;

    private boolean validacaoDescricao;
    private boolean validacaoValor;
    private boolean validacaoNotaAlcancada;
    private boolean validacaoNomeTarefa;
    private boolean validacaoMeta;
    private boolean validacaoDataEntrega;
    private boolean validacaoHoraEntrega;

    private Calendar calendar;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle("Adicionar tarefas");
        it = getIntent();
        extra = it.getExtras();
        user = extra.getString("user");
        String user_nomeDisciplina = extra.getString("user");
        String [] parts = user_nomeDisciplina.split("/");
        nomeUser = parts[0];
        nomeDisciplina = parts[1];
        Log.e("user", user);
        setContentView(R.layout.adicionar_tarefa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etNomeDisciplina = findViewById(R.id.etNomeDisciplina);
        etNomeTarefa = findViewById(R.id.etNomeTarefa);
        etDescricao = findViewById(R.id.etDescricao);
        etValor = findViewById(R.id.etValor);
        etMeta = findViewById(R.id.etMeta);
        etNotaAlcancada = findViewById(R.id.etNotaAlcancada);
        etDataEntrega = findViewById(R.id.etDataEntrega);
        dateFormat = DateFormat.getInstance();
        calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getDefault());
        dialogDatePicker = new DatePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view,
                                          int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        //set textDate
                        etDataEntrega.setText(dayOfMonth + "/" + month +"/"+year);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        etHoraEntrega = findViewById(R.id.etHoraEntrega);
        dateFormatTime = DateFormat.getTimeInstance();
        etHoraEntrega.setText(dateFormatTime.format(calendar.getTime()));
        dialogTimePicker = new TimePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        //set textTime
                        etHoraEntrega.setText(hourOfDay + ":" + minute);
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);

        etNomeDisciplina.setText(nomeDisciplina);
        etNomeTarefa.setText("");
        etDescricao.setText("");
        etValor.setText("");
        etMeta.setText("");
        etNotaAlcancada.setText("");
        etNomeDisciplina.requestFocus();


        }
        public void showDatePicker(View view) {
            dialogDatePicker.show();
        }

        public void showTimePicker(View view) {
            dialogTimePicker.show();
        }

    public void adicionarTarefa(View view) {


         validacaoDescricao = Util.isEmpty(etDescricao);
         validacaoNomeTarefa = Util.isEmpty(etNomeTarefa);
         validacaoValor = Util.isEmpty(etValor);
         validacaoNotaAlcancada = Util.isEmpty(etNotaAlcancada);
         validacaoMeta = Util.isEmpty(etMeta);
         validacaoDataEntrega = Util.isEmpty(etDataEntrega);
         validacaoHoraEntrega = Util.isEmpty(etHoraEntrega);

         Log.e("validacao",""+validacaoDescricao +
        validacaoNomeTarefa +
        validacaoValor +
        validacaoNotaAlcancada +
        validacaoMeta +
        validacaoDataEntrega +
        validacaoHoraEntrega );

        if (validacaoNomeTarefa) {
            Toast.makeText(this, "O nome da tarefa deve ser preenchida", Toast.LENGTH_LONG).show();
            etNomeTarefa.setText("");
            etNomeTarefa.requestFocus();
        } else if (validacaoValor) {
             Toast.makeText(this, "O valor deve ser preenchido", Toast.LENGTH_LONG).show();
             etValor.setText("");
             etValor.requestFocus();
        } else if (validacaoDataEntrega){
             Toast.makeText(this, "A data de entrega deve ser preenchida", Toast.LENGTH_LONG).show();
             etDataEntrega.setText("");
             etDataEntrega.requestFocus();
        } else {
             descricao = etDescricao.getText().toString();
             valor = Double.parseDouble(etValor.getText().toString());
             dataEntrega = etDataEntrega.getText().toString();
             tarefa = new Tarefa(descricao,dataEntrega,valor);

            if(!validacaoNomeTarefa){
                nomeTarefa = etNomeTarefa.getText().toString();
                tarefa.setNomeTarefa(nomeTarefa);
            } else {
                tarefa.setNota(null);
            }
             if(!validacaoNotaAlcancada){
                 notaAlcancada = Double.parseDouble(etNotaAlcancada.getText().toString());
                 tarefa.setNota(notaAlcancada);
             } else {
                 tarefa.setNota(null);
             }

             if(!validacaoMeta){
                 meta = Double.parseDouble(etMeta.getText().toString());
                 tarefa.setMetaNota(meta);
             } else {
                 tarefa.setMetaNota(null);
             }

             if(!validacaoHoraEntrega){
                 horaEntrega = etHoraEntrega.getText().toString();
                 tarefa.setHoraEntrega(horaEntrega);
             } else {
                 tarefa.setHoraEntrega(null);
             }
            escreverNovaTarefa(tarefa, user);


            it = getIntent();
            extra = it.getExtras();
            Intent it = new Intent(AdicionarTarefa.this, ListarTarefa.class);
            it.putExtra("user", user);
            finish();
            startActivity(it);

        }


    }

    public void escreverNovaTarefa(Tarefa tarefa, String user){

        Log.e( "Nova Tarefa",tarefa.toString());
        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
        Log.e("Path", "Alunos/" + user + "/" + tarefa.getNomeTarefa());
        raiz.child("Alunos/" + user + "/tarefas/" + tarefa.getNomeTarefa()).setValue(tarefa);

        try {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent notificationIntent = new Intent(this, AlarmReceiver.class);
            notificationIntent.putExtra("tarefa", tarefa.getNomeTarefa());
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Date alarmeAtividade = new SimpleDateFormat("dd/MM/yyyy").parse(tarefa.getDataEntrega());
            Calendar cal = Calendar.getInstance();
            cal.setTime(alarmeAtividade);
//            cal.add(Calendar.DATE, -1);
            Log.e("data", String.valueOf(cal));
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        } catch (ParseException e) {
            e.printStackTrace();
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
        Intent it = new Intent(AdicionarTarefa.this, ListarTarefa.class);
        it.putExtra("user", user);
        finish();
        startActivity(it);
        super.onBackPressed();
    }

}
