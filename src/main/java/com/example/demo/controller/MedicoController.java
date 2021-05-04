package com.example.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.model.Medico;
import com.example.demo.service.MedicoServico;

@Controller
@RequestMapping(path = "/sig")
public class MedicoController {
	Logger logger = LogManager.getLogger(MedicoController.class);
	@Autowired
	MedicoServico servico;

	@GetMapping("/medicos")
	public ModelAndView retornaFormDeConsultaTodosMedicos() {
		ModelAndView modelAndView = new ModelAndView("consultarMedico");
		modelAndView.addObject("medicos", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/medico")
	public ModelAndView retornaFormDeCadastroDe(Medico medico) {
		ModelAndView mv = new ModelAndView("cadastrarMedico");
		mv.addObject("medico", medico);
		return mv;
	}

	@GetMapping("/medico/{cpf}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarMedico(@PathVariable("cpf") String cpf) {
		ModelAndView modelAndView = new ModelAndView("atualizarMedico");
		modelAndView.addObject("medico", servico.findByCpf(cpf)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view }
	}

	@GetMapping("/medico/{id}")
	public ModelAndView excluirNoFormDeConsultaMedico(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id =>  " + id);
		ModelAndView modelAndView = new ModelAndView("consultarMedico");
		modelAndView.addObject("medicos", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/medicos")
	public ModelAndView save(Medico medico, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarMedico");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarMedico");
		} else {
			modelAndView = servico.saveOrUpdate(medico);
		}
		return modelAndView;
	}

	@PostMapping("/medico/{id}")
	public ModelAndView atualizaMedico(@PathVariable("id") Long id, Medico medico, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarMedico");
		if (result.hasErrors()) {
			medico.setId(id);
			return new ModelAndView("atualizarMedico");
		} 
			// programacao defensiva - deve-se verificar se o Medico existe antes de
			// atualizar Medico umMedico = servico.findById(id);
			// umMedico.setCpf(medico.getCpf()); umMedico.setNome(medico.getNome());
			// umMedico.setEmail(medico.getEmail()); umMedico.setCep(medico.getCep());
			// modelAndView = servico.saveOrUpdate(umMedico); return modelAndView; } }
		return modelAndView;
	}
}
