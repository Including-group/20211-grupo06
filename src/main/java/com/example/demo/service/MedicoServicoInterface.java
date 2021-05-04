package com.example.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.model.Medico;
import com.example.demo.model.MedicoRepository;
import com.example.demo.model.Endereco;

@Service
public class MedicoServicoInterface implements MedicoServico {
	Logger logger = LogManager.getLogger(MedicoServicoInterface.class);
	@Autowired
	private MedicoRepository repository;

	public Iterable<Medico> findAll() {
		return repository.findAll();
	}

	public Medico findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Medico findById(Long id) {
		return repository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Medico medico) {
		ModelAndView modelAndView = new ModelAndView("consultarMedico");
		try {
			String endereco = obtemEndereco(medico.getCep());
			if (endereco != "") {
				medico.setEndereco(endereco);
				repository.save(medico);
				logger.info(">>>>>> 4. comando save executado  ");
				modelAndView.addObject("clientes", repository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarMedico");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - medico já cadastrado.");
				logger.info(">>>>>> 5. medico ja cadastrado ==> " + e.getMessage());
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