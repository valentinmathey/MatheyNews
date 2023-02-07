/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mathey.news.controladores;

import com.mathey.news.entidades.Noticia;
import com.mathey.news.excepciones.MiException;
import com.mathey.news.servicios.NoticiaSerivicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author VALENTIN
 */

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {
    
    @Autowired
    private NoticiaSerivicio noticiaServicio;
    
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo){
        
        List <Noticia> noticias = noticiaServicio.listarNoticias();
        
        modelo.addAttribute("noticias", noticias);
        
        return "inicio.html";
    }
    
    @GetMapping("/registrar") //localhost:8080/noticia/registrar
    public String registrar(){
        return "noticia_form.html";
    }
    
    @PostMapping("/registro") //localhost:8080/noticia/registro
    public String registro(@RequestParam("titulo") String titulo,@RequestParam("cuerpo") String cuerpo, ModelMap modelo){
        
        try {
            
            noticiaServicio.crearNoticia(titulo, cuerpo);
            
            modelo.put("exito", "La noticia fue registrada correctamente!");
        } catch (MiException ex) {
                      
            modelo.put("error", ex.getMessage());
            return "noticia_form.html";
        }
        
        return "index.html";        
    }
    
    @GetMapping("/vista/{id}") //localhost:8080/noticia
    public String verNoticia(@PathVariable("id") String id, ModelMap modelo){
        
        modelo.addAttribute("noticia", noticiaServicio.getOne(id));
        
        return "noticia.html";
    }
    
    
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") String id, ModelMap modelo){
        
        modelo.put("noticia", noticiaServicio.getOne(id));
        
        return "noticia_modificar.html";
    }
    
    @PostMapping("/modificado/{id}")
    public String modificado(@PathVariable("id") String id,@RequestParam("titulo") String titulo,@RequestParam("cuerpo") String cuerpo, ModelMap modelo){
        
        System.out.println(id + titulo + cuerpo);
        
        try {
            noticiaServicio.modificarNoticia(titulo, cuerpo, id);
                      
            return "redirect:/noticia/inicio";
            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_modificar.html";
        }
        
    }
    
    @DeleteMapping("{id}")
    public String eliminar(@PathVariable("id") String id, ModelMap modelo) throws MiException{
        
        noticiaServicio.eliminarNoticia(id);
        
        return "noticia_modificar.html";
    }
}
