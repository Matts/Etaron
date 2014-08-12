/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class AccesService.java
 * 
 **/

package nl.dalthow.etaron.service;

import nl.dalthow.etaron.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://service.etaron.dalthow.nl")
@SOAPBinding
public interface AccessService 
{
	// Declaration
	
    @WebMethod
    @WebResult(name = "version")
    String getVersion();

    @WebMethod(operationName = "login")
    @WebResult(name = "user")
    User login(@WebParam(name = "username") String username, @WebParam(name = "password") String password);

    @WebMethod(operationName = "saveSettings")
    void saveSettings(@WebParam(name = "user") User user);

    @WebMethod(operationName = "saveScores")
    void saveScores(@WebParam(name = "user") User user);
}
