package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Consulta{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres") 
	private String cpf;
	private String crmMedico;
	private String horarioConsulta;
	private String dataConsulta;


	public Consulta() {

	}

	public Consulta(@NotNull String cpf, @NotNull String nome, @NotNull String email, @NotNull String cep) {
		this.cpf = cpf; 
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getHorarioConsulta() {
		return horarioConsulta;
	}

	public void setHorarioConsulta(String horarioConsulta) {
		this.horarioConsulta = horarioConsulta;
	}

	public String getCrmMedico() {
		return crmMedico;
	}

	public void setCrmMedico(String crmMedico) {
		this.crmMedico = crmMedico;
	}

	public String getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(String dataConsulta) {
		this.dataConsulta = dataConsulta;
	}


}
