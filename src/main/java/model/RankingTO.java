/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Empleado;

/**
 *
 * @author arnauu
 */
public class RankingTO {
    private String empleado;
    private int Incidencias;

    public RankingTO(String empleado, int Incidencias) {
        this.empleado = empleado;
        this.Incidencias = Incidencias;
    }
    
    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

 

    public int getIncidencias() {
        return Incidencias;
    }

    public void setIncidencias(int Incidencias) {
        this.Incidencias = Incidencias;
    }
    
}
