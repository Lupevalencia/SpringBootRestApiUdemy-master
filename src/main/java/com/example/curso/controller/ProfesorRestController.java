package com.example.curso.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.curso.entity.Profesor;
import com.example.curso.mapper.Mapper;
import com.example.curso.model.MProfesor;
import com.example.curso.service.IProfesorService;

@RestController
@RequestMapping("/api") //Etiqueta que nos permite hacer peticiones. Las cuáles después lanzaremos a postman
public class ProfesorRestController {
	
	@Autowired
	private IProfesorService profesorService; //Nos traemos la interfaces
	
	@GetMapping("/profesores")  //Devolvemos la lista de profesores. api/profesores. GET
	@ResponseStatus(HttpStatus.OK)
	public List<Profesor> getProfesores(){
		return profesorService.findAll();
	}
	
	@PostMapping("/find_professor")
	public ResponseEntity<?> findProfesor(@RequestBody Profesor profesor){
		Profesor profesorDb = profesorService.findProfesor(profesor);
		if(profesorDb!=null) {
			return new ResponseEntity<>(profesorDb, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/sign_up")
	public ResponseEntity<Void> addProfesor(@RequestBody Profesor profesor){
		if(profesorService.findProfesor(profesor)==null) {
			profesorService.save(profesor);
			return new ResponseEntity<Void>(HttpStatus.CREATED); //Haciendo click en CREATED podremos acceder al número con el que se lanzará la petición al postman
		}else {													  // Igual necesito algún import, debería estar en otro color
			return new ResponseEntity<Void>(HttpStatus.CONFLICT); //Nos avisa de que no se ha podido crear porque ha entrado en conflicto
		}
	}


	
	@PostMapping("login")
	public ResponseEntity<?> loginProfesor(@RequestBody Profesor profesor){
		Profesor profesorDb = profesorService.checkProfesorLogin(profesor);
		if(profesorDb!=null) {
			List<Profesor> profesores = new ArrayList<>();
			profesores.add(profesorDb);
			List<MProfesor> mProfesores = new ArrayList<>();
			mProfesores = Mapper.convertirLista(profesores);
			return new ResponseEntity<>(mProfesores, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	//Actualizar un profesor. Le pasamos por parámetros un id. Escribimos <?> cuando no sabemos lo que devuelve
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProfesor(@PathVariable(value="id")Long id, @RequestBody Profesor profesor){
		Profesor profesorDb = null;
		profesorDb = profesorService.findById(id); //Lo buscamos en la base de datos
		if(profesorDb != null) {
			profesorDb.setEmail(profesor.getEmail());   //La contraseña no por	que no nos interesa
			profesorDb.setNombre(profesor.getNombre());
			profesorDb.setFoto(profesor.getFoto());
			profesorService.uptadeProfesor(profesorDb); //Actualiza
			return new ResponseEntity<>(profesorDb, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update_sql")  //Aquí cogemos el id que nos viene con profesor
	public ResponseEntity<?> updateProfesorSql(@RequestBody Profesor profesor){
		Profesor profesorDb = null;
		profesorDb = profesorService.findByIdSQL(profesor.getId());
		if(profesorDb != null) {
			profesorDb.setEmail(profesor.getEmail());
			profesorDb.setNombre(profesor.getNombre());
			profesorDb.setFoto(profesor.getFoto());
			profesorService.uptadeProfesor(profesorDb);
			return new ResponseEntity<>(profesorDb, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProfesor(@PathVariable(value="id")Long id){
		profesorService.deleteProfesor(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<Void> deleteAllProfesor(){
		profesorService.deleteAllProfesor();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("delete_post")
	public ResponseEntity<Void> deleteProfesorPost(@RequestBody Profesor profesor){
		if(profesorService.findProfesor(profesor)!=null) {
			profesorService.deleteProfesor(profesor);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
}
