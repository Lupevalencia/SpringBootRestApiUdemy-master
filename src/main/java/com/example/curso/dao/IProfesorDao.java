package com.example.curso.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.curso.entity.Profesor;

public interface IProfesorDao extends CrudRepository<Profesor, Long> {

	public Profesor findByEmail(String email); //Buscamos por email
	
	public Profesor findByEmailAndPassword(String email, String password); //Buscamos por email y contraseña
	
	public Optional<Profesor> findById(Long id);
	
	@Query("select p from Profesor p where p.id=?1")  //Por que aquí no utuliza
	public Profesor findByIdSQL(Long id);
		
}
