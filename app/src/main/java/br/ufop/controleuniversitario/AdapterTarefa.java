package br.ufop.controleuniversitario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTarefa extends ArrayAdapter<Tarefa> {

    public AdapterTarefa(Context context, ArrayList<Tarefa> tarefas){
        super(context, 0, tarefas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Tarefa tarefa = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.
                    from(getContext()).
                    inflate((R.layout.tarefa_adapter), parent,false);
        }

        TextView tvTarefa = convertView.findViewById(R.id.tvTarefa);
        TextView tvNota = convertView.findViewById(R.id.tvNota);
        TextView tvDataEntrega = convertView.findViewById(R.id.tvDataEntrega);

        tvTarefa.setText(tarefa.getNomeTarefa());
        tvNota.setText(tarefa.getNota() + "/" + tarefa.getValor());
        tvDataEntrega.setText(tarefa.getDataEntrega());


        return convertView;
    }
}
