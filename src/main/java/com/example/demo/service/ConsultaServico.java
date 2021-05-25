package com.example.demo.service;

import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Consulta;

public interface ConsultaServico {
	public Iterable<Consulta> findAll();

	public Consulta findByCpf(String cpf);

	public void deleteById(Long id);

	public Consulta findById(Long id);

	public ModelAndView saveOrUpdate(Consulta consulta);

	public String obtemEndereco(String cep);
}