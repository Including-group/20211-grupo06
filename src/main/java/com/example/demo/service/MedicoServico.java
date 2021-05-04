package com.example.demo.service;

import org.springframework.web.servlet.ModelAndView;
import com.example.demo.model.Medico;

public interface MedicoServico {
	public Iterable<Medico> findAll();

	public Medico findByCpf(String cpf);

	public void deleteById(Long id);

	public Medico findById(Long id);

	public ModelAndView saveOrUpdate(Medico medico);

	public String obtemEndereco(String cep);
}