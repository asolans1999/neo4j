/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author arnauu
 */

import daoNeo.Dao;
import enums.TipoIncidencia;
import exceptions.NeoExceptions;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleado;


public class vista {
    
    private static Dao daoNeo = Dao.initNeo();
    private static Empleado logueado = null;
    
    public static void main(String[] args){
        boolean exit = false;
        do{
            int menu;
            try{
                if (logueado!=null) {
                    System.out.println("< Welcome " +logueado.getFullName()+" >");
                    System.out.println("0.Log off");
                    System.out.println("1.Put incidence");
                    menu = InputAsker.askInt("Choose option", 0, 1);
                    switch(menu){
                        case 1:
                            incidencia();
                            break;
                        case 0:
                            System.out.println("Good bye !");
                            logueado = null;
                            break;   
                    }
                }else{
                    initMenu();
                    menu = InputAsker.askInt("Choose option", 0, 3);
                    switch(menu){
                        case 1:
                            login();
                            break;
                        case 2:
                            registration();
                            break;       
                        case 3:
                            daoNeo.close();
                            System.out.println("Good bye! ");
                            exit= true;
                            break;
                    }    
                }
            }catch(NeoExceptions | ParseException ex){
                System.out.println(ex.getMessage());
            }
        }while(!exit);
    }
    
    private static void registration() throws NeoExceptions{
        String userName = InputAsker.askString("Username: ");
        if (daoNeo.existeEmpleado(userName)) {
            throw new NeoExceptions(NeoExceptions.EMPLEADO_EXISTS);
        }
        String pass = InputAsker.askString("Password: ");
        String pass2 = InputAsker.askString("Confirm password: ");
        if (!pass.equals(pass2)) {
            throw new NeoExceptions(NeoExceptions.NO_COINCIDEN);
        }
        
        String fullName = InputAsker.askString("Full name: ");
        String phone = InputAsker.askString("Phone: ");
        
        if (phone.length()!=9) {
            throw new NeoExceptions(NeoExceptions.INCORRECT_TLF);
        }
        Empleado e = new Empleado(userName, pass, fullName, phone);
        daoNeo.insertEmpleado(e);
        System.out.println("Registration correct");
    }
    
    private static void incidencia() throws NeoExceptions, ParseException{
        int id = InputAsker.askInt("Id de incidencia: ", 0, Integer.MAX_VALUE);
        if (daoNeo.existeIncidencia(id)) {
           throw new NeoExceptions(NeoExceptions.INCIDENCIA_EXISTS); 
        }
        String origen = InputAsker.askString("Origen: ");
        String destino = InputAsker.askString("Destino: ");
        String descr = InputAsker.askString("Descripción: ");
        System.out.println("1. Normal");
        System.out.println("2. Urgente");
        int esc = InputAsker.askInt("Choose type of incidence", 1, 2);
        switch(esc){
            case 1:
                TipoIncidencia tipo = TipoIncidencia.NORMAL;
                break;
            case 2:
                tipo = TipoIncidencia.URGENTE;
                break;
        }
        Date d = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String date = format1.format(d);
        Date date1 = format1.parse(date);
        //acabar insertar incidencia
    }
    
    private static void login() throws NeoExceptions{
        String userName = InputAsker.askString("Username: ");
        String pass = InputAsker.askString("Password: ");
        logueado = daoNeo.loguinEmpleado(userName, pass);
    }
    
    private static void initMenu(){
        System.out.println("<< NEO4J >>");
        System.out.println("1.Log in");
        System.out.println("2.Registration");
        System.out.println("3. Exit");
    }
    
}
