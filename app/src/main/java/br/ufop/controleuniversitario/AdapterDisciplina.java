package br.ufop.controleuniversitario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterDisciplina extends ArrayAdapter<Disciplina> {


    public  AdapterDisciplina(Context context, ArrayList<Disciplina> disciplinas){
        super (context, 0, disciplinas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Disciplina disciplina = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.
                          from(getContext()).
                          inflate((R.layout.disciplina_adapter), parent, false);
        }

        TextView tvDisciplina = convertView.findViewById(R.id.tvDisciplina);
        TextView tvNota = convertView.findViewById(R.id.tvNota);
        TextView tvFaltas = convertView.findViewById(R.id.tvFalta);
        TextView tvAprovado = convertView.findViewById(R.id.tvAprovado);

        tvDisciplina.setText(disciplina.getNomeDisciplina());
        tvNota.setText(disciplina.getNotaAtual() + "/100" );
        tvFaltas.setText(disciplina.getNumeroFaltasAtual() + "/" + disciplina.getLimiteFaltas());
        if(disciplina.aprovado()){
            tvAprovado.setText("sim");
        } else {
            tvAprovado.setText("não");
        }

        return convertView;
    }
}
