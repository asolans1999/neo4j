/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enums.TipoEvento;
import java.util.Date;

/**
 *
 * @author arnauu
 */
public class Evento {
    private TipoEvento tipoEvento;
    private Date dateTime;
    private Empleado empleado;

    public Evento(TipoEvento tipoEvento, Date dateTime, Empleado empleado) {
        this.tipoEvento = tipoEvento;
        this.dateTime = dateTime;
        this.empleado = empleado;
    }

    public Evento() {
        
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    
    
}
