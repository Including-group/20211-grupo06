package com.example.demo.service;

import org.springframework.web.servlet.ModelAndView;
import com.example.demo.model.Paciente;

public interface PacienteServico {
	public Iterable<Paciente> findAll();

	public Paciente findByCpf(String cpf);

	public void deleteById(Long id);

	public Paciente findById(Long id);

	public ModelAndView saveOrUpdate(Paciente medico);

	public String obtemEndereco(String cep);
}