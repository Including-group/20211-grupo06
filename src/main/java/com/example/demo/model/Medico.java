package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Medico{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres") 
	@Column(unique = true)
	private String cpf;
	@NotNull
	private String crm;
	@NotNull
	private String nome;
	@NotNull
	private String email;
	private String logradouroHospital;
	private String cargaHoraria;
	private String endereco;
	private String cep;

	public Medico() {

	}

	public Medico(@NotNull String cpf, @NotNull String crm, @NotNull String nome, @NotNull String email, @NotNull String cep) {
		this.cpf = cpf; 
		this.crm = crm;
		this.nome = nome; 
		this.email = email;
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

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogradouroHospital() {
		return logradouroHospital;
	}

	public void setLogradouroHospital(String logradouroHospital) {
		this.logradouroHospital = logradouroHospital;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "Medico [cpf=" + cpf + ", crm=" + crm + ", nome=" + nome + ", email=" + email + ", logradouroHospital="
				+ logradouroHospital + ", cargaHoraria=" + cargaHoraria + ", endereco=" + endereco + ", cep=" + cep
				+ "]";
	}

}
