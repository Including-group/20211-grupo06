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
import com.example.demo.service.PacienteServico;
import com.example.demo.model.Paciente;

@Controller
@RequestMapping(path = "/sig")
public class PacienteController {
	Logger logger = LogManager.getLogger(PacienteController.class);
	@Autowired
	PacienteServico servico;

	@GetMapping("/pacientes")
	public ModelAndView retornaFormDeConsultaTodosPacientes() {
		ModelAndView mv = new ModelAndView("consultarPaciente");
		mv.addObject("pacientes", servico.findAll());	
		return mv;
	}

	@GetMapping("/paciente")
	public ModelAndView retornaFormDeCadastroDePaciente(Paciente paciente) {
		ModelAndView mv = new ModelAndView("cadastrarPaciente");
		mv.addObject("Paciente", paciente);
		return mv;
	}

	@GetMapping("/paciente/editView/{cpf}")
	public ModelAndView retornaFormParaEditarPaciente(@PathVariable("cpf") String cpf) {
		ModelAndView mv = new ModelAndView("atualizarPaciente");
		mv.addObject("paciente", servico.findByCpf(cpf));
		return mv;
	}

	@GetMapping("/paciente/{id}")
	public ModelAndView excluirNoFormDeConsultaPaciente(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id =>  " + id);
		ModelAndView mv = new ModelAndView("consultarPaciente");
		mv.addObject("pacientes", servico.findAll());
		return mv;
	}

	@PostMapping("/pacientes")
	public ModelAndView save(Paciente paciente, BindingResult result) {
		ModelAndView mv = new ModelAndView("consultarPaciente");
		
		try {
			if (result.hasErrors()) {
				mv.setViewName("cadastrarPaciente");
			} else {
				mv = servico.saveOrUpdate(paciente);
				mv.addObject("pacientes", servico.findAll());
				mv.setViewName("consultarPaciente");
			}
			return mv;
		} catch (Exception e) {
			return  new ModelAndView("cadastrarPaciente");
		}
		
	}

	@PostMapping("/paciente/edit/{id}")
	public ModelAndView atualizaPaciente(@PathVariable("id") Long id, @Valid Paciente paciente, BindingResult result) {
		ModelAndView mv = new ModelAndView("consultarPaciente");
		if (result.hasErrors()) {
			paciente.setId(id);
			return new ModelAndView("atualizarPaciente");
		} 
		
		Paciente umPaciente = servico.findById(id); 
		umPaciente.setCpf(paciente.getCpf()); 
		umPaciente.setNome(paciente.getNome()); 
		umPaciente.setEmail(paciente.getEmail()); 
		umPaciente.setCep(paciente.getCep());
		umPaciente.setEndereco(paciente.getEndereco());
		umPaciente.setTelefone(paciente.getTelefone());
		umPaciente.setObservacao(paciente.getObservacao());
		mv = servico.saveOrUpdate(umPaciente);
		
		mv.addObject("pacientes", servico.findAll());
		mv.setViewName("consultarPaciente");
		return mv; 
	}
}
