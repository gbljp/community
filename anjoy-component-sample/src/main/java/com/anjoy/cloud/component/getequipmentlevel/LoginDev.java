package getequipmentlevel;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import _134._0._168._192.ormrpc.services.EASLogin.EASLoginProxy;
import _134._0._168._192.ormrpc.services.EASLogin.EASLoginProxyServiceLocator;
import client.WSContext;

public class LoginDEV {
	
	private static final String USER_NAME = "0057233";
	private static final String PASSWORD = "504359";
	private static final String S_IN_NAME = "eas";
	private static final String LANGUAGE = "L2";
	private static final String DC_NAME = "DEV";
	private static final int DB_TYPE = 2;

	/**
	 * µÇÂ¼EAS
	 * @throws ServiceException
	 * @throws RemoteException
     */
    public String EASLogin() throws ServiceException, RemoteException  {
		EASLoginProxyServiceLocator easLoginProxyServiceLocator = new EASLoginProxyServiceLocator();
		EASLoginProxy easLoginProxy = easLoginProxyServiceLocator.getEASLogin();
		WSContext context = easLoginProxy.login(USER_NAME, PASSWORD, S_IN_NAME, DC_NAME, LANGUAGE, DB_TYPE);
		String sessionId = context.getSessionId();
		return sessionId;
    }

}
