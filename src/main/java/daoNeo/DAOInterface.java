/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoNeo;

import model.RankingTO;
import exceptions.NeoExceptions;
import java.text.ParseException;
import java.util.List;
import model.Empleado;
import model.Evento;
import model.Incidencia;

/**
 *
 * @author arnauu
 */
public interface DAOInterface {

    // Método para insertar un nuevo empleado.
    public void insertEmpleado(Empleado e);

    // Método para validar el login de un empleado.
    public Empleado loginEmpleado(String user, String pass) throws NeoExceptions;

    // Método para modificar el perfil de un empleado.
    public void updateEmpleado(Empleado e);

    // Método para eliminar un empleado.
    public void removeEmpleado(Empleado e);

    // Obtener una Incidencia a partir de su Id.
    public Incidencia getIncidenciaById(int id);

    // Obtener una lista de todas las incidencias
    public List<Incidencia> selectAllIncidencias();

    // Insertar una incidencia a partir de un objeto incidencia
    public void insertIncidencia(Incidencia i) throws ParseException;

    // Obtener la lista de incidencias con destino un determinado
    // empleado, a partir de un objeto empleado.
    public List<Incidencia> getIncidenciaByDestino(Empleado e) throws ParseException;

    // Obtener la lista de incidencias con origen un determinado
    // empleado, a partir de un objeto empleado.
    public List<Incidencia> getIncidenciaByOrigen(Empleado e) throws ParseException;

    // Método para insertar un evento en la tabla historial.
    // Pasaremos como parámetro un objeto tipo evento, y no devolverá nada.
    // Llamaremos a este método desde los métodos
    // que producen los eventos, que son 3:
    // 1) Cuando un usuario hace login 
    // 2) Cuando un usuario crea una incidencia de tipo urgente 
    // 3) Cuando se consultan las incidencias destinadas a un usuario 
    public void insertarEvento(Evento e);

    // Obtener la fecha-hora del último inicio de sesión para un empleado.
    public Evento getUltimoInicioSesion(Empleado e);

    // Obtener el ranking de los empleados por cantidad de incidencias
    // urgentes creadas (más incidencias urgentes primero).
    public List<RankingTO> getRankingEmpleados();

}
