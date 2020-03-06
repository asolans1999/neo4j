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
    public static final String NO_COINCIDEN= "< ERROR: Passwords don't match >";
    public static final String INCORRECT_TLF= "< ERROR: Incorrect phone format >";
    public static final String INCORRECT_LOGUIN= "< ERROR: Incorrect loguin > ";



    
    public NeoExceptions(String message) {
        super(message);
    }
    
}
