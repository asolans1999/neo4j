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
    public void insertEmpleado(String name, String password, String fullname, String phone){
        
        Session session = driver.session();
        //session.run("CREATE (empleado: Empleado {name: {name}, password: {password}, fullname: {fullname}, phone: {phone})",parameters("name", name, "password", password,"fullname",fullname,"phone",phone));
        session.run("CREATE (empleado:Empleado {name: "+name+", password: "+ password + ",fullname: "+ fullname + ", phone: "+ phone + "}");
    }
    
    public void printPeople(String initial)
    {
        try (Session session = driver.session())
        {
            // A Managed Transaction transactions are a quick and easy way to wrap a Cypher Query.
            // The `session.run` method will run the specified Query.
            // This simpler method does not use any automatic retry mechanism.
            Result result = session.run(
                    "MATCH (a:Person) WHERE a.name STARTS WITH $x RETURN a.name AS name",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            while (result.hasNext())
            {
                Record record = result.next();
                // Values can be extracted from a record by index or name.
                System.out.println(record.get("name").asString());
            }
        }
    }
    public void close() throws Exception
    {
       driver.close();
    }
}
