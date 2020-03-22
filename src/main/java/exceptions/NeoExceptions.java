/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author arnauu
 */
public class NeoExceptions extends Exception {
    
    public static final String EMPLEADO_EXISTS= "< ERROR: Name existing >";
    public static final String INCIDENCIA_EXISTS= "< ERROR: Incidence id existing >";
    public static final String NO_COINCIDEN= "< ERROR: Passwords don't match >";
    public static final String INCORRECT_TLF= "< ERROR: Incorrect phone format >";
    public static final String INCORRECT_LOGUIN= "< ERROR: Incorrect loguin > ";
    public static final String INCORRECT_PASS= "< ERROR: Incorrect password > ";
    public static final String NO_EXISTS_EMPLEADOS= "< ERROR: Employes don't exists > ";
    public static final String INCIDENCE_BY_ORIGIN= "< ERROR: Doesn't exist incidence of your origin > ";
    public static final String INCIDENCE_BY_DESTINATION= "< ERROR: Doesn't exist incidence of your destination > ";
    public static final String INCIDENCE_BY_ID= "< ERROR: Doesn't exist incidence > ";
    



    
    public NeoExceptions(String message) {
        super(message);
    }
    
}
