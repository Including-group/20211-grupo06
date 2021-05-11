package com.example.demo.controller;

import javax.validation.Valid;

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
import com.example.demo.service.MedicoServico;
import com.example.demo.model.Medico;

@Controller
@RequestMapping(path = "/sig")
public class MedicoController {
	Logger logger = LogManager.getLogger(MedicoController.class);
	@Autowired
	MedicoServico servico;

	@GetMapping("/medicos")
	public ModelAndView retornaFormDeConsultaTodosMedicos() {
		ModelAndView mv = new ModelAndView("consultarMedico");
		mv.addObject("medicos", servico.findAll());
		return mv;
	}

	@GetMapping("/medico")
	public ModelAndView retornaFormDeCadastroDe(Medico medico) {
		ModelAndView mv = new ModelAndView("cadastrarMedico");
		mv.addObject("medico", medico);
		return mv;
	}

	@GetMapping("/medico/{cpf}")
	public ModelAndView retornaFormParaEditarMedico(@PathVariable("cpf") String cpf) {
		ModelAndView mv = new ModelAndView("atualizarMedico");
		mv.addObject("medico", servico.findByCpf(cpf));
		return mv;
	}

	@GetMapping("/medico/{id}")
	public ModelAndView excluirNoFormDeConsultaMedico(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id =>  " + id);
		ModelAndView mv = new ModelAndView("consultarMedico");
		mv.addObject("medicos", servico.findAll());
		return mv;
	}

	@PostMapping("/medicos")
	public ModelAndView save(Medico medico, BindingResult result) {
		ModelAndView mv = new ModelAndView("consultarMedico");
		
		if (result.hasErrors()) {
			mv.setViewName("cadastrarMedico");
		} else {
			System.out.println("MEDICOOOOOOOOOOOOOOOOOOO:" + medico);
			mv = servico.saveOrUpdate(medico);
		}
		return mv;
	}

	@PostMapping("/medicos/{id}")
	public ModelAndView atualizaMedico(@PathVariable("id") Long id, @Valid Medico medico, BindingResult result) {
		ModelAndView mv = new ModelAndView("consultarMedico");
		if (result.hasErrors()) {
			medico.setId(id);
			return new ModelAndView("atualizarMedico");
		} 
		
		// programacao defensiva - deve-se verificar se o Cliente existe antes de atualizar
		Medico umMedico = servico.findById(id); 
		umMedico.setCpf(medico.getCpf()); 
		umMedico.setNome(medico.getNome()); 
		umMedico.setEmail(medico.getEmail()); 
		umMedico.setCep(medico.getCep());
		mv = servico.saveOrUpdate(umMedico);
		
		return mv; 
	}
}
