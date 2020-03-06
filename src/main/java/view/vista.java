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
import exceptions.NeoExceptions;
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
                    menu = InputAsker.askInt("Choose option", 0, 4);
                    switch(menu){
                        case 0:
                            System.out.println("Good bye !");
                            logueado = null;
                            break;   
                    }
                }else{
                    initMenu();
                    menu = InputAsker.askInt("Choose option", 0, 2);
                    switch(menu){
                        case 1:
                            login();
                            break;
                        case 2:
                            registration();
                            break;                 
                    }    
                }
            }catch(NeoExceptions ex){
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
    
    private static void login() throws NeoExceptions{
        String userName = InputAsker.askString("Username: ");
        String pass = InputAsker.askString("Password: ");
        logueado = daoNeo.loguinEmpleado(userName, pass);
    }
    
    private static void initMenu(){
        System.out.println("<< NEO4J >>");
        System.out.println("1.Log in");
        System.out.println("2.Registration");
    }
    
}
