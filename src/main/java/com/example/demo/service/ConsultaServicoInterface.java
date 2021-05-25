package com.example.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.model.Consulta;
import com.example.demo.model.ConsultaRepository;

@Service
public class ConsultaServicoInterface implements ConsultaServico {
	Logger logger = LogManager.getLogger(ConsultaServicoInterface.class);
	@Autowired
	private ConsultaRepository repository;

	public Iterable<Consulta> findAll() {
		return repository.findAll();
	}

	public Consulta findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Consulta findById(Long id) {
		return repository.findById(id).get();
	}
	
	@Transactional
	public ModelAndView saveOrUpdate(Consulta consulta) {
		ModelAndView modelAndView = new ModelAndView("consultarConsulta");
		try {
			repository.save(consulta);
			logger.info(">>>>>> 4. comando save executado  ");
			modelAndView.addObject("medico", repository.findAll());
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarConsulta");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - consulta já cadastrado.");
				logger.info(">>>>>> 5. consulta ja cadastrado ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 5. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView;
	}

	@Override
	public String obtemEndereco(String cep) {
		// TODO Auto-generated method stub
		return null;
	}

}