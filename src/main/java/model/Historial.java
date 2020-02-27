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
public class Historial {
    private TipoEvento tipoEvento;
    private Date dateTime;
    private Empleado empledo;

    public Historial(TipoEvento tipoEvento, Date dateTime, Empleado empledo) {
        this.tipoEvento = tipoEvento;
        this.dateTime = dateTime;
        this.empledo = empledo;
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

    public Empleado getEmpledo() {
        return empledo;
    }

    public void setEmpledo(Empleado empledo) {
        this.empledo = empledo;
    }
    
    
    
}
