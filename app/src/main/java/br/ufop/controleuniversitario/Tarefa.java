package br.ufop.controleuniversitario;

public class Tarefa {
    //Atributos

    private String descricao;
    private String dataEntrega;
    private String horaEntrega;
    private String nomeTarefa;
    private Double valor;
    private Double nota;
    private Double metaNota;

    public Tarefa(){

    }

    public Tarefa(String descricao, String dataEntrega, Double valor) {
        this.descricao = descricao;
        this.dataEntrega = dataEntrega;
        this.valor = valor;
    }

    public Tarefa(String descricao, String dataEntrega, String horaEntrega, String nomeTarefa, Double valor, Double nota, Double metaNota) {
        this.descricao = descricao;
        this.dataEntrega = dataEntrega;
        this.horaEntrega = horaEntrega;
        this.nomeTarefa = nomeTarefa;
        this.valor = valor;
        this.nota = nota;
        this.metaNota = metaNota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public Double getMetaNota() {
        return metaNota;
    }

    public void setMetaNota(Double metaNota) {
        this.metaNota = metaNota;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    @Override
    public String toString() {
        return (
                "\nNome da tarefa = " + getNomeTarefa() +
                "\nDescriçao = " + getDescricao() +
                "\nValor = " + getValor() +
                "\nNota = " + getNota() +
                "\nData de entrega = " + getDataEntrega() +
                "\nPrioridade = " + getMetaNota()) +
                "\nHora de Entrega = " + getHoraEntrega();
    }
}
