package com.example.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Endereco;
import com.example.demo.model.Paciente;
import com.example.demo.model.PacienteRepository;

@Service
public class PacienteServicoInterface implements PacienteServico {
	Logger logger = LogManager.getLogger(PacienteServicoInterface.class);
	@Autowired
	private PacienteRepository repository;

	public Iterable<Paciente> findAll() {
		return repository.findAll();
	}

	public Paciente findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Paciente findById(Long id) {
		return repository.findById(id).get();
	}
	
	@Transactional
	public ModelAndView saveOrUpdate(Paciente paciente) {
		ModelAndView modelAndView = new ModelAndView("consultarPaciente");
		try {
			String endereco = obtemEndereco(paciente.getCep());
			if (endereco != "") {
				paciente.setEndereco(endereco);
				repository.save(paciente);
				logger.info(">>>>>> 4. comando save executado  ");
				modelAndView.addObject("medico", repository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarPaciente");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - paciente já cadastrado.");
				logger.info(">>>>>> 5. paciente ja cadastrado ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 5. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView;
	}

	public String obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		Endereco endereco = template.getForObject(url, Endereco.class, cep);
		logger.info(">>>>>> 3. obtem endereco ==> " + endereco.toString());
		return endereco.getLogradouro();
	}
}