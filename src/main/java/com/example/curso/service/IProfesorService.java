package com.example.curso.service;

import java.util.List;
import java.util.Optional;

import com.example.curso.entity.Profesor;

public interface IProfesorService {  //Diferentes opciones
	
	public List<Profesor> findAll();
	
	public void save(Profesor profesor);
	
	public Profesor findProfesor(Profesor profesor);
	
	public Profesor checkProfesorLogin(Profesor profesor);
	
	public void deleteProfesor(Profesor profesor);
	
	public Profesor uptadeProfesor(Profesor profesor);
	
	public Optional<Profesor> findProfesorById(Long profesor_id);
	
	public void deleteProfesor(Long id);
	
	public void deleteAllProfesor(); //Aquí necesio ir añadiendo lo que necesito en ProfesorRestCotroller.java
										// y después implementar estos métodos aquí añadidos. Más arrancar el proyecto
	public Profesor findById(Long id);
	
	public Profesor findByIdSQL(Long id);
	
}
