package com.example.demo.service;

import java.util.Iterator;

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
	String existConsultaError = "Este medico ja possui consulta neste dia e periodo.";
	@Autowired
	MedicoServico mServico;

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
			Iterable<Consulta> consultas = repository.findAll();
			Iterator<Consulta> itr =  consultas.iterator();
			
			while(itr.hasNext()) {
				Consulta cosultatinha = itr.next();
				String dataC = cosultatinha.getDataConsulta();
				String crmC = cosultatinha.getCrmMedico();
				String periodoC = cosultatinha.getHorarioConsulta();
				
				if(dataC.equalsIgnoreCase(consulta.getDataConsulta()) 
						&& crmC.equalsIgnoreCase(consulta.getCrmMedico()) 
						&& periodoC.equalsIgnoreCase(consulta.getHorarioConsulta())) {
					consultaExist();
				}
			}
			
			repository.save(consulta);
			logger.info(">>>>>> 4. comando save executado  ");
			modelAndView.addObject("consultas", repository.findAll());
			modelAndView.setViewName("consultarConsulta");
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarConsulta");
			if(e.getMessage().contains(existConsultaError)) {
				modelAndView.addObject("message", existConsultaError);
				modelAndView.addObject("cpfPaciente", consulta.getCpf());
				modelAndView.addObject("medicos", mServico.findAll());
				modelAndView.setViewName("cadastrarConsulta");
				return modelAndView;
			} else if (e.getMessage().contains("could not execute statement")) {
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
	
	public void consultaExist() throws IllegalAccessException {
		logger.info(">>>>>> 5. Erro consutla ja marcada");
		throw new IllegalAccessException(existConsultaError);
	}

}