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
    private String origen;
    private String destino;
    private String descripcion;
    private TipoIncidencia tipoIncidencia;
    private Empleado empleado;

    public Incidencia(int id, Date datetime, String origen, String destino, String descripcion, TipoIncidencia tipoIncidencia, Empleado empleado) {
        this.id = id;
        this.datetime = datetime;
        this.origen = origen;
        this.destino = destino;
        this.descripcion = descripcion;
        this.tipoIncidencia = tipoIncidencia;
        this.empleado = empleado;
    }

    public int getId() {
        return id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }
    
    
    
    
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
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
