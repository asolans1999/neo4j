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


public class vista {
    
    private static Dao daoNeo = Dao.initNeo();
    
    public static void main(String[] args){
        boolean exit = false;
        do{
            int menu;
            //try{
                initMenu();
                menu = InputAsker.askInt("Choose option", 0, 2);
                switch(menu){
                    case 1:
                        //log in
                        break;
                    case 2:
                        registration();
                        break;                 
                }    
            //}catch(NeoExceptions ex){
            //    System.out.println(ex.getMessage());
            //}
            
        }while(!exit);
    }
    
    private static void registration(){
        String userName = InputAsker.askString("Username: ");
        String pass = InputAsker.askString("Password: ");
        String pass2 = InputAsker.askString("Confirm password: ");
        String fullName = InputAsker.askString("Full name: ");
        String phone = InputAsker.askString("Phone: ");
        daoNeo.insertEmpleado(userName,pass,fullName,phone);
        
    }
    
    private static void initMenu(){
        System.out.println("1.Log in");
        System.out.println("2.Registration");
    }
}
