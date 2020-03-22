/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoNeo;

/**
 *
 * @author arnauu
 */
import model.RankingTO;
import enums.TipoEvento;
import enums.TipoIncidencia;
import exceptions.NeoExceptions;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleado;
import model.Evento;
import model.Incidencia;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

import static org.neo4j.driver.Values.parameters;


public class Dao implements DAOInterface {
    
    private final Driver driver;
    private static Dao initConex;
    
    private Dao(){
        driver = GraphDatabase.driver( "bolt://localhost:7687" , AuthTokens.basic( "neo4j", "1234") );
    }
    
    public static Dao initNeo(){  
        if (initConex == null) {
            initConex = new Dao();
        }
        return initConex;
    }
    public void insertEmpleado(Empleado e){      
        Session session = driver.session();
        session.run("CREATE (Empleado:empleado {name: '"+e.getUserName()+"', password: '"+ e.getPassword() + "',fullname: '"+ e.getFullName() + "', phone: '"+ e.getPhone() + "'})");
    }
    
    @Override
    public void insertIncidencia(Incidencia i )throws ParseException{
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String date = format1.format(i.getDatetime());
        Session session = driver.session();
        session.run("CREATE (Incidencia:incidencia {id: '"+i.getId()+"', datetime: '"+ date + "',origen: '"+ i.getOrigen().getUserName() + "', destino: '"+ i.getDestino().getUserName() + "', descripcion: '"+ i.getDescripcion()+"', tipo: '"+ i.getTipoIncidencia().toString()+"'})");
        session.run("MATCH (a:empleado),(b:incidencia) WHERE a.name = '"+i.getOrigen().getUserName()+"' and b.id='"+i.getId()+"' CREATE (a)-[: origin]->(b)");
        session.run("MATCH (a:incidencia),(b:empleado) WHERE a.id = '"+i.getId()+"' and b.name='"+i.getDestino().getUserName()+"' CREATE (a)-[: destination]->(b)");
        if (i.getTipoIncidencia().URGENTE.toString().equals("URGENTE") ) {
            Date d = new Date();
            Evento evento = new Evento(TipoEvento.U,d,i.getOrigen());
            insertarEvento(evento);
        }
    }
    
    
    @Override
    public Empleado loginEmpleado(String n, String pass) throws NeoExceptions{
        Session session = driver.session();
        Result result = session.run("MATCH (n:empleado) where n.name = '"+n+"' RETURN n.password,n.fullname,n.phone");
        if (!result.hasNext()) {
            throw new NeoExceptions(NeoExceptions.INCORRECT_LOGUIN);
        }
        Record record = result.next();
        if (!record.get("n.password").asString().equals(pass)) {
            throw new NeoExceptions(NeoExceptions.INCORRECT_LOGUIN);
        }
        Empleado e = new Empleado(n,pass,record.get("n.fullname").asString(),record.get("n.phone").asString());
        Date d = new Date();
        Evento evento = new Evento(TipoEvento.I,d,e);
        insertarEvento(evento);
        return e;
    }
    
    public List<Empleado> returnEmpleados(){
        List<Empleado>  empleados = new ArrayList<>();
        Session session = driver.session();
        Result result = session.run("match (n:empleado) return n.name, n.password, n.fullname, n.phone");
        while(result.hasNext()){
            Record record = result.next();
            Empleado e = new Empleado(record.get("n.name").asString(),record.get("n.password").asString(),record.get("n.fullname").asString(),record.get("n.phone").asString());
            empleados.add(e);
        }
        return empleados;
        
    }
    
    public boolean existeEmpleado(String nombre){
        Session session = driver.session();
        Result result = session.run("MATCH (n:empleado) where n.name = '"+nombre+"' RETURN n");
        while(result.hasNext()){
            return true;
        }
        return false;
    }
    
    public boolean existeIncidencia(int id){
        Session session = driver.session();
        Result result = session.run("MATCH (n:incidencia) where n.id = '"+id+"' RETURN n");
        while(result.hasNext()){
            return true;
        }
        return false;
    }
    
    @Override
    public void updateEmpleado(Empleado e){
        Session session = driver.session();
        Result result = session.run("MERGE (p:empleado {name: '"+e.getUserName()+"'}) SET p.fullname = '"+e.getFullName()+"', p.phone = '"+e.getPhone()+"', p.password = '"+e.getPassword()+"'");
    }
    public List<Incidencia> getIncidenciaByOrigen(Empleado e) throws ParseException{
        List<Incidencia>  incidencias = new ArrayList<>();
        Session session = driver.session();
        Result result = session.run("MATCH (a:incidencia) WHERE a.origen = '"+e.getUserName()+"' return a.destino,a.datetime,a.descripcion,a.tipo,a.id");
        while(result.hasNext()){
            Record record = result.next();
            Incidencia i = new Incidencia();
            i.setId(Integer.valueOf(record.get("a.id").asString()));
            Empleado destino = new Empleado();
            destino.setUserName(record.get("a.destino").asString());
            i.setDestino(destino);
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date d = format1.parse(record.get("a.datetime").asString());
            i.setDatetime(d);
            i.setDescripcion(record.get("a.descripcion").asString());
            i.setTipoIncidencia(TipoIncidencia.valueOf(record.get("a.tipo").asString()));
            incidencias.add(i);
        }
        return incidencias;
    }
    
    @Override
    public List<Incidencia> getIncidenciaByDestino(Empleado e) throws ParseException{
        List<Incidencia>  incidencias = new ArrayList<>();
        Session session = driver.session();
        Result result = session.run("MATCH (a:incidencia) WHERE a.destino = '"+e.getUserName()+"' return a.origen,a.datetime,a.descripcion,a.tipo,a.id");
        while(result.hasNext()){
            Record record = result.next();
            Incidencia i = new Incidencia();
            i.setId(Integer.valueOf(record.get("a.id").asString()));
            Empleado origen = new Empleado();
            origen.setUserName(record.get("a.origen").asString());
            i.setOrigen(origen);
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date d = format1.parse(record.get("a.datetime").asString());
            i.setDatetime(d);
            i.setDescripcion(record.get("a.descripcion").asString());
            i.setTipoIncidencia(TipoIncidencia.valueOf(record.get("a.tipo").asString()));
            incidencias.add(i);
        }
        Date d = new Date();
        Evento evento = new Evento(TipoEvento.C,d,e);
        insertarEvento(evento);
        return incidencias;
    }
    
    public void close()
    {
       driver.close();
    }
    
    //Encontrar un nodo por el id : MATCH (n:empleado) where  ID(n) = 0  return n
    //Devuelve el id de un nodo: MATCH (n:empleado) where n.name = 'arnau'RETURN ID(n)
    //Eliminar un nodo: MATCH (n:empleado{ name: 'funciona' }) DELETE n
    //MATCH (a:empleado),(b:incidencia) WHERE a.name = 'arnau' and b.id='1' CREATE (a)-[: put]->(b)
    //Eliminar una relacion : MATCH (n { name: 'arnau' })-[r:put]->() DELETE r
    //Modificar : MERGE (p:empleado {name: 'arnau'}) SET p.fullname = 'arnau solans', p.phone = '111111111'
    //MATCH (a:incidencia) WHERE a.origen = 'arnau' or a.destino = 'arnau' return a

    @Override
    public void removeEmpleado(Empleado e) {
        Session session = driver.session();
        Result result = session.run("MATCH (n { name: '"+e.getUserName()+"' }) DETACH DELETE n");
    }

    @Override
    public Incidencia getIncidenciaById(int id) {
        Incidencia  i = new Incidencia();
        Session session = driver.session();
        Result result = session.run("MATCH (a:incidencia) WHERE a.id='"+id+ "'return a.destino,a.origen, a.datetime,a.descripcion,a.tipo,a.id");
        while(result.hasNext()){
            Record record = result.next();
            i.setId(Integer.valueOf(record.get("a.id").asString()));
            Empleado destino = new Empleado();
            destino.setUserName(record.get("a.destino").asString());
            i.setDestino(destino);
            Empleado origen = new Empleado();
            origen.setUserName(record.get("a.origen").asString());
            i.setOrigen(origen);
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = format1.parse(record.get("a.datetime").asString());
            } catch (ParseException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
            i.setDatetime(d);
            i.setDescripcion(record.get("a.descripcion").asString());
            i.setTipoIncidencia(TipoIncidencia.valueOf(record.get("a.tipo").asString()));    
        }
        return i;
    }

    @Override
    public List<Incidencia> selectAllIncidencias() {
        List<Incidencia>  incidencias = new ArrayList<>();
        Session session = driver.session();
        Result result = session.run("MATCH (a:incidencia) return a.destino,a.origen, a.datetime,a.descripcion,a.tipo,a.id");
        while(result.hasNext()){
            Record record = result.next();
            Incidencia i = new Incidencia();
            i.setId(Integer.valueOf(record.get("a.id").asString()));
            Empleado destino = new Empleado();
            destino.setUserName(record.get("a.destino").asString());
            i.setDestino(destino);
            Empleado origen = new Empleado();
            origen.setUserName(record.get("a.origen").asString());
            i.setOrigen(origen);
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = format1.parse(record.get("a.datetime").asString());
            } catch (ParseException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
            i.setDatetime(d);
            i.setDescripcion(record.get("a.descripcion").asString());
            i.setTipoIncidencia(TipoIncidencia.valueOf(record.get("a.tipo").asString()));
            incidencias.add(i);
        }
        return incidencias;
    }
    
    @Override
    public void insertarEvento(Evento e) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = format1.format(e.getDateTime());
        Session session = driver.session();
        session.run("CREATE (Historial:historial {tipo: '"+e.getTipoEvento().toString()+"', date: '"+ date + "',empleado: '"+ e.getEmpleado().getUserName() + "'})");
        session.run("MATCH (a:historial),(b:empleado) WHERE a.date = '"+date+"' and b.name='"+e.getEmpleado().getUserName()+"' CREATE (a)-[: event]->(b)");
    }

    @Override
    public Evento getUltimoInicioSesion(Empleado e) {
        List<Evento>  eventos = new ArrayList<>();
        Session session = driver.session();
        Result result = session.run("MATCH (a:historial) WHERE a.tipo='I' return a.tipo,a.date, a.empleado");
        while(result.hasNext()){
            Record record = result.next();
            Evento event = new Evento();
            event.setTipoEvento(TipoEvento.valueOf(record.get("a.tipo").asString()));
            record.get("a.date").asString();
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date d = null;
            try {
                d = format1.parse(record.get("a.date").asString());
            } catch (ParseException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
            event.setDateTime(d);
            event.setEmpleado(e);
            eventos.add(event);
        }
        Date fechaUltima = eventos.get(0).getDateTime(); 
        Evento ev = eventos.get(0);
        for (Evento event : eventos) {
            if (fechaUltima.before(event.getDateTime())) {
                fechaUltima = event.getDateTime();
                ev = event;
            }
        }
        
        return ev;
    }

    @Override
    public List<RankingTO> getRankingEmpleados() {
        List<RankingTO> ranking = new ArrayList<>();
        List<Empleado> empleados = returnEmpleados();
        for (Empleado e: empleados) {
            int count = 0;
            Session session = driver.session();
            Result result = session.run("MATCH (a:incidencia) WHERE a.origen = '"+e.getUserName()+"' return a.origen,a.destino,a.datetime,a.descripcion,a.tipo,a.id");
            while(result.hasNext()){
                Record record = result.next();
                if (record.get("a.origen").asString().equals(e.getUserName())) {
                    count++;
                }
            } 
            RankingTO rt = new RankingTO(e.getUserName(), count);
            ranking.add(rt);
            System.out.println(rt.getIncidencias() + " " + rt.getEmpleado());
        }
        System.out.println("salir");
        return ranking;
    }
    
    public List<Incidencia> selectAllIncidenciasForUser(Empleado e) {
        List<Incidencia>  incidencias = new ArrayList<>();
        Session session = driver.session();
        Result result = session.run("MATCH (a:incidencia) WHERE a.origen = '"+e.getUserName()+"' or a.destino = '"+e.getUserName()+"' return a.destino,a.origen, a.datetime,a.descripcion,a.tipo,a.id");
        while(result.hasNext()){
            Record record = result.next();
            Incidencia i = new Incidencia();
            i.setId(Integer.valueOf(record.get("a.id").asString()));
            Empleado destino = new Empleado();
            destino.setUserName(record.get("a.destino").asString());
            i.setDestino(destino);
            Empleado origen = new Empleado();
            origen.setUserName(record.get("a.origen").asString());
            i.setOrigen(origen);
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = format1.parse(record.get("a.datetime").asString());
            } catch (ParseException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
            i.setDatetime(d);
            i.setDescripcion(record.get("a.descripcion").asString());
            i.setTipoIncidencia(TipoIncidencia.valueOf(record.get("a.tipo").asString()));
            incidencias.add(i);
        }
        return incidencias;
    }
    
}
