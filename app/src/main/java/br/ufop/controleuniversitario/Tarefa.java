package br.ufop.controleuniversitario;

public class Tarefa {
    //Atributos

    private String descricao;
    private double valor;
    private double nota;
    private String dataEntrega;
    private String tipoAtividade;
    private double metaNota;

    public Tarefa(){

    }
    public Tarefa(String descricao, double valor, double nota,String dataEntrega, String tipoAtividade, double metaNota) {
        this.descricao = descricao;
        this.valor = valor;
        this.nota = nota;
        this.dataEntrega = null;
        this.tipoAtividade = tipoAtividade;
        this.metaNota = metaNota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega( String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public double getMetaNota() {
        return metaNota;
    }

    public void setMetaNota(double metaNota) {
        this.metaNota = metaNota;
    }

    @Override
    public String toString() {
        return ("\nDescriçao = " + getDescricao() +
                "\nValor = " + getValor() +
                "\nNota = " + getNota() +
                "\nData de entrega = " + getDataEntrega() +
                "\nTipo = " + getTipoAtividade() +
                "\nPrioridade = " + getMetaNota());
    }
}
