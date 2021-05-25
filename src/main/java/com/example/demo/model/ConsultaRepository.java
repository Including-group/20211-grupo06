package com.example.demo.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends CrudRepository<Consulta, Long> {
	public Consulta findByCpf(@Param("cpf") String cpf);
}
