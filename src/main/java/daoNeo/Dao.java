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
import exceptions.NeoExceptions;
import java.util.LinkedHashMap;
import java.util.Map;
import model.Empleado;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

import static org.neo4j.driver.Values.parameters;


public class Dao implements AutoCloseable {
    
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
    
    public Empleado loguinEmpleado(String n, String pass) throws NeoExceptions{
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
        return e;

    }
    
    public boolean existeEmpleado(String nombre){
        Session session = driver.session();
        Result result = session.run("MATCH (n:empleado) where n.name = '"+nombre+"' RETURN n");
        while(result.hasNext()){
            return true;
        }
        return false;
    }
    
    public void printPeople(String name)
    {
        try (Session session = driver.session())
        {
            Result result = session.run("MATCH (n:empleado {name:'"+name+"'}) RETURN n.name, n.fullname, n.phone");
            while(result.hasNext()){
                Record record = result.next();
                System.out.println(record.get("n.name").asString());
                System.out.println(record.get("n.fullname").asString());
                System.out.println(record.get("n.phone").asString());
            }
        }
    }
    public void close() throws Exception
    {
       driver.close();
    }
}
