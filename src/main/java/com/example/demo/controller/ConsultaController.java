package com.example.demo.controller;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.ConsultaServico;
import com.example.demo.service.MedicoServico;
import com.example.demo.model.Consulta;
import com.example.demo.model.Paciente;

@Controller
@RequestMapping(path = "/sig")
public class ConsultaController {
	Logger logger = LogManager.getLogger(ConsultaController.class);
	@Autowired
	ConsultaServico servico;
	@Autowired
	MedicoServico mServico;

	@GetMapping("/consultas")
	public ModelAndView retornaFormDeConsultaTodosMedicos() {
		ModelAndView mv = new ModelAndView("consultarConsulta");
		mv.addObject("consultas", servico.findAll());
		return mv;
	}

	@GetMapping("/consulta/{cpf}")
	public ModelAndView retornaFormParaEditarPaciente(@PathVariable("cpf") String cpfPaciente, Consulta consulta) {
		ModelAndView mv = new ModelAndView("cadastrarConsulta");
		mv.addObject("consulta", consulta);
		mv.addObject("cpfPaciente", cpfPaciente);
		mv.addObject("medicos", mServico.findAll());
		return mv;
	}
	
	@GetMapping("/consulta/editView/{id}")
	public ModelAndView retornaFormParaEditarPaciente(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("atualizarConsulta");
		mv.addObject("consulta", servico.findById(id));
		mv.addObject("medicos", mServico.findAll());
		return mv;
	}
	
	@GetMapping	("/consultaD/{id}")
	public ModelAndView excluirNoFormDeConsultaPaciente(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id =>  " + id);
		ModelAndView mv = new ModelAndView("consultarConsulta");
		mv.addObject("consultas", servico.findAll());
		return mv;
	}

	@PostMapping("/consultas")
	public ModelAndView save(Consulta consulta, BindingResult result) {
		ModelAndView mv = new ModelAndView("consultarConsulta");
		
		try {
			if (result.hasErrors()) {
				mv.setViewName("cadastrarConsulta");
			} else {
				mv = servico.saveOrUpdate(consulta);
			}
			return mv;
		} catch (Exception e) {
			return  new ModelAndView("cadastrarConsulta");
		}
	}
	
	@PostMapping("/consulta/edit/{id}")
	public ModelAndView atualizaPaciente(@PathVariable("id") Long id, @Valid Consulta consulta, BindingResult result) {
		ModelAndView mv = new ModelAndView("consultarPaciente");
		if (result.hasErrors()) {
			consulta.setId(id);
			return new ModelAndView("atualizarConsulta");
		} 
		
		Consulta umaConsulta = servico.findById(id); 
		umaConsulta.setCpf(consulta.getCpf()); 
		umaConsulta.setCrmMedico(consulta.getCrmMedico()); 
		umaConsulta.setHorarioConsulta(consulta.getHorarioConsulta()); 
		umaConsulta.setDataConsulta(consulta.getDataConsulta());
		mv = servico.saveOrUpdate(umaConsulta);
		
		mv.addObject("consultas", servico.findAll());
		mv.setViewName("consultarConsulta");
		return mv; 
	}
	
}
