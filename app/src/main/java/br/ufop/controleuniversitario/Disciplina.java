package br.ufop.controleuniversitario;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class Disciplina {
    //Atributos

    private String nomeDisciplina;
    private String semestre;
    private Integer numeroFaltasAtual;
    private Integer limiteFaltas;
    private Double metaNota;
    private Boolean andamento;
    private Double notaAtual;
    private ArrayList<Tarefa> tarefa;
    //Atributos Opcionais

    private String diaSemana;
    private String horarioAula;
    private String diaSemana2;
    private String horarioAula2;
    private String professor;
    private String emailProfessor;

    public Disciplina(){

    }


    public Disciplina(String nomeDisciplina, String semestre, Integer limiteFaltas, Double metaNota, Boolean andamento, ArrayList<Tarefa> tarefa) {
        this.nomeDisciplina = nomeDisciplina;
        this.semestre = semestre;
        this.numeroFaltasAtual = 0;
        this.limiteFaltas = limiteFaltas;
        this.metaNota = metaNota;
        this.andamento = andamento;
        this.diaSemana = null;
        this.horarioAula = null;
        this.professor = null;
        this.emailProfessor = null;
        this.notaAtual = 0.0;
        this.tarefa = tarefa;
    }

    public Disciplina(String nomeDisciplina, String semestre, Integer numeroFaltasAtual, Integer limiteFaltas, Double metaNota, Boolean andamento, Double notaAtual, ArrayList<Tarefa> tarefa, String diaSemana, String horarioAula, String diaSemana2, String horarioAula2, String professor, String emailProfessor) {
        this.nomeDisciplina = nomeDisciplina;
        this.semestre = semestre;
        this.numeroFaltasAtual = numeroFaltasAtual;
        this.limiteFaltas = limiteFaltas;
        this.metaNota = metaNota;
        this.andamento = andamento;
        this.notaAtual = notaAtual;
        this.tarefa = tarefa;
        this.diaSemana = diaSemana;
        this.horarioAula = horarioAula;
        this.diaSemana2 = diaSemana2;
        this.horarioAula2 = horarioAula2;
        this.professor = professor;
        this.emailProfessor = emailProfessor;
    }

    //Getters & Setters
    public String getNomeDisciplina() { return nomeDisciplina; }

    public void setNomeDisciplina(String nomeDisciplina) { this.nomeDisciplina = nomeDisciplina; }

    public String getSemestre() { return semestre; }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Integer getNumeroFaltasAtual() {
        return numeroFaltasAtual;
    }

    public void setNumeroFaltasAtual(Integer numeroFaltas) {
        this.numeroFaltasAtual = numeroFaltas;
    }

    public Integer getLimiteFaltas() {
        return limiteFaltas;
    }

    public void setLimiteFaltas(Integer limiteFaltas) {
        this.limiteFaltas = limiteFaltas;
    }

    public Double getMetaNota() {
        return metaNota;
    }

    public void setMetaNota(Double metaNota) {
        this.metaNota = metaNota;
    }

    public Boolean isAndamento() {
        return andamento;
    }

    public void setAndamento(Boolean andamento) {
        this.andamento = andamento;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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

    public Double getNotaAtual() {
        return notaAtual;
    }

    public void setNotaAtual(Double notaAtual) {
        this.notaAtual = notaAtual;
    }

    public String getDiaSemana2() {
        return diaSemana2;
    }

    public void setDiaSemana2(String diaSemana2) {
        this.diaSemana2 = diaSemana2;
    }

    public String getHorarioAula2() {
        return horarioAula2;
    }

    public void setHorarioAula2(String horarioAula2) {
        this.horarioAula2 = horarioAula2;
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

    public Boolean aprovado(){
        return notaAtual >= 60 && numeroFaltasAtual <= limiteFaltas;
    }



    @Override
    public String toString() {
        return ("\nnome = "  + this.getNomeDisciplina() +
                "\nSemestre = " + this.getSemestre() +
                "\nFaltas = " + this.getNumeroFaltasAtual() +
                "\nLimite de faltas = " + this.getLimiteFaltas() +
                "\nMeta = " + this.getMetaNota()+
                "\nAndamento = " + this.isAndamento() +
                "\nDias de aula = " + this.getDiaSemana() +
                "\nHorário de aula = " + this.getHorarioAula() +
                "\nDias de aula 2 = " + this.getDiaSemana2() +
                "\nHorário de aula 2 = " + this.getHorarioAula2() +
                "\nProfessor = " + this.getProfessor() +
                "\nEmail do Profesor = " + this.getEmailProfessor() +
                "\nNumero nota atual = " + this.getNotaAtual() +
                "\nTarefas = " + this.getTarefa());
    }
}
