package com.example.demo.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends CrudRepository<Medico, Long> {
	public Medico findByCpf(@Param("cpf") String cpf);
}
