package br.ufop.controleuniversitario;

import android.util.Log;

import java.util.ArrayList;

public class Disciplina {
    //Atributos
    private String nomeDisciplina;
    private int semestre;
    private int numeroFaltasAtual;
    private int limiteFaltas;
    private double metaNota;
    private boolean andamento;
    private double notaAtual;
    private ArrayList<Tarefa> tarefa;
    //Atributos Opcionais

    private String diasDeAula;
    private String horarioAula;
    private String professor;
    private String emailProfessor;

    public Disciplina(){

    }


    public Disciplina(String nomeDisciplina, int semestre, int limiteFaltas, double metaNota, boolean andamento, ArrayList<Tarefa> tarefa) {
        this.nomeDisciplina = nomeDisciplina;
        this.semestre = semestre;
        this.numeroFaltasAtual = 0;
        this.limiteFaltas = limiteFaltas;
        this.metaNota = metaNota;
        this.andamento = andamento;
        this.diasDeAula = null;
        this.horarioAula = null;
        this.professor = null;
        this.emailProfessor = null;
        this.notaAtual = 0;
        this.tarefa = tarefa;
    }

    public Disciplina(String nomeDisciplina, int semestre, int numeroFaltasAtual, int limiteFaltas, double metaNota, boolean andamento, double notaAtual, ArrayList<Tarefa> tarefa, String diasDeAula, String horarioAula, String professor, String emailProfessor) {
        this.nomeDisciplina = nomeDisciplina;
        this.semestre = semestre;
        this.numeroFaltasAtual = numeroFaltasAtual;
        this.limiteFaltas = limiteFaltas;
        this.metaNota = metaNota;
        this.andamento = andamento;
        this.notaAtual = notaAtual;
        this.tarefa = tarefa;
        this.diasDeAula = diasDeAula;
        this.horarioAula = horarioAula;
        this.professor = professor;
        this.emailProfessor = emailProfessor;
    }

    //Getters & Setters
    public String getNomeDisciplina() { return nomeDisciplina; }

    public void setNomeDisciplina(String nomeDisciplina) { this.nomeDisciplina = nomeDisciplina; }

    public int getSemestre() { return semestre; }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getNumeroFaltasAtual() {
        return numeroFaltasAtual;
    }

    public void setNumeroFaltasAtual(int numeroFaltas) {
        this.numeroFaltasAtual = numeroFaltas;
    }

    public int getLimiteFaltas() {
        return limiteFaltas;
    }

    public void setLimiteFaltas(int limiteFaltas) {
        this.limiteFaltas = limiteFaltas;
    }

    public double getMetaNota() {
        return metaNota;
    }

    public void setMetaNota(double metaNota) {
        this.metaNota = metaNota;
    }

    public boolean isAndamento() {
        return andamento;
    }

    public void setAndamento(boolean andamento) {
        this.andamento = andamento;
    }

    public String getDiasDeAula() {
        return diasDeAula;
    }

    public void setDiasDeAula(String diasDeAula) {
        this.diasDeAula = diasDeAula;
    }

    public String getHorarioAula() {
        return horarioAula;
    }

    public void setHorarioAula(String horarioAula) {
        this.horarioAula = horarioAula;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getEmailProfessor() {
        return emailProfessor;
    }

    public void setEmailProfessor(String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }

    public ArrayList<Tarefa> getTarefa() {
        return tarefa;
    }

    public void setTarefa(ArrayList<Tarefa> tarefa) {
        this.tarefa = tarefa;
    }

    public double getNotaAtual() {
        return notaAtual;
    }

    public void setNotaAtual(double notaAtual) {
        this.notaAtual = notaAtual;
    }

    public String numeroFaltasPossiveis(){

        int numFaltasPossiveis = this.limiteFaltas - this.numeroFaltasAtual;
        Log.d("numFaltasPossiveis", String.valueOf(numFaltasPossiveis));

        if(numFaltasPossiveis < 0) {
            return ("Reprovado por falta!");
        }else{
            return String.valueOf(numFaltasPossiveis);
        }
    }

    public String notaNecessariaParaPassar(){
        double notaNessesaria = 60 - this.notaAtual;

        if(notaNessesaria <= 0){
            return ("Já aprovado!");
        }else{
            return (String.valueOf(notaNessesaria));
        }


    }

    public String notaNecessariaParaAtingirMeta(){
        double notaNessesaria = this.metaNota - this.notaAtual;

        if(notaNessesaria <= 0){
            return ("Meta já atingida!");
        }else{
            return (String.valueOf(notaNessesaria));
        }

    }

    @Override
    public String toString() {
        return ("\nnome = "  + this.getNomeDisciplina() +
                "\nSemestre = " + this.getSemestre() +
                "\nFaltas = " + this.getNumeroFaltasAtual() +
                "\nLimite de faltas = " + this.getLimiteFaltas() +
                "\nMeta = " + this.getMetaNota()+
                "\nAndamento = " + this.isAndamento() +
                "\nDias de aula = " + this.getDiasDeAula() +
                "\nHorário de aula = " + this.getHorarioAula() +
                "\nProfessor = " + this.getProfessor() +
                "\nEmail do Profesor = " + this.getEmailProfessor() +
                "\nNumero nota atual = " + this.getNotaAtual() +
                "\nTarefas = " + this.getTarefa().toString());
    }
}
