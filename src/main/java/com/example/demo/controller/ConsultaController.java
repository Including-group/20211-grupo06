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

import com.example.demo.service.ConsultaServico;
import com.example.demo.service.MedicoServico;
import com.example.demo.model.Consulta;

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

	@PostMapping("/consultas")
	public ModelAndView save(Consulta consulta, BindingResult result) {
		ModelAndView mv = new ModelAndView("consultarConsulta");
		
		try {
			if (result.hasErrors()) {
				mv.setViewName("cadastrarConsulta");
			} else {
				mv = servico.saveOrUpdate(consulta);
				mv.addObject("consultas	", servico.findAll());
				mv.setViewName("consultarConsulta");
			}
			return mv;
		} catch (Exception e) {
			return  new ModelAndView("cadastrarConsulta");
		}
	}
	
}
