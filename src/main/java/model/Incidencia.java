/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enums.TipoIncidencia;
import java.util.Date;

/**
 *
 * @author arnauu
 */
public class Incidencia {
    private int id;
    private Date datetime;
    private Empleado origen;
    private Empleado destino;
    private String descripcion;
    private TipoIncidencia tipoIncidencia;

    public Incidencia(int id, Date datetime, Empleado origen, Empleado destino, String descripcion, TipoIncidencia tipoIncidencia) {
        this.id = id;
        this.datetime = datetime;
        this.origen = origen;
        this.destino = destino;
        this.descripcion = descripcion;
        this.tipoIncidencia = tipoIncidencia;
    }

    public Incidencia() {
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
        
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Empleado getOrigen() {
        return origen;
    }

    public void setOrigen(Empleado origen) {
        this.origen = origen;
    }

    public Empleado getDestino() {
        return destino;
    }

    public void setDestino(Empleado destino) {
        this.destino = destino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }
    
    
    
}
