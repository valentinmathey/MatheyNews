/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mathey.news.servicios;

import com.mathey.news.entidades.Noticia;
import com.mathey.news.excepciones.MiException;
import com.mathey.news.respositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALENTIN
 */
@Service
public class NoticiaSerivicio {

    @Autowired
    NoticiaRepositorio noticiaRepositorio;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo) throws MiException {

        validar(titulo, cuerpo);

        Noticia noticia = new Noticia();

        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setFechaSubida(new Date());

        noticiaRepositorio.save(noticia);
    }
    
    @Transactional
    public void modificarNoticia(String titulo, String cuerpo, String id) throws MiException{
        
        validar(titulo, cuerpo);
        
        Optional<Noticia> respuestaNoticia = noticiaRepositorio.findById(id);
        
        if(respuestaNoticia.isPresent()){
            
            System.out.println("entrar");
            
            Noticia noticia = respuestaNoticia.get();
                  
            noticia.setTitulo(titulo);
            
            noticia.setCuerpo(cuerpo);

            //noticia.getFechasModificacion().add(new Date());
            
            noticiaRepositorio.save(noticia);
            
        }
    }
    
    @Transactional(readOnly = true)
    public List<Noticia> listarNoticias() {

        List<Noticia> noticias = new ArrayList();

        noticias = noticiaRepositorio.findAll();

        return noticias;
    }
    
    @Transactional(readOnly = true)
    public Noticia getOne(String id){
        return noticiaRepositorio.getOne(id);
    }
    
    @Transactional
    public void eliminarNoticia(String id) throws MiException{
        
        Noticia noticia = noticiaRepositorio.getById(id);
        
        noticiaRepositorio.delete(noticia);

    }

    private void validar(String titulo, String cuerpo) throws MiException {
        
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacio");
        }
        
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("el cuerpo no puede ser nulo o estar vacio");
        }
        
    }
}
