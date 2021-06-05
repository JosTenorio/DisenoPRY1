/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import com.profesorfalken.jpowershell.*;

/**
 *
 * @author JOS
 */
public class Proxy {
    private static final String proxyDir = "C:\\Users\\JOS\\Desktop\\DisenoPRY1\\Restaurant POS";
    private static final String proxyCommand = ".//cloud_sql_proxy -instances=hallowed-trail-313100:us-central1:sgr-prototype=tcp:1433";
    private static PowerShell PowerShellSession = null;
    
    
    private static void openPSSession () {
        if (PowerShellSession == null)
            PowerShellSession = PowerShell.openSession();
    }
    
    public static void closeProxy () {
            if (PowerShellSession != null)
                PowerShellSession.close();
    }
    
    public static void openProxy () {
       openPSSession();
       PowerShellResponse response = PowerShellSession.executeCommand(".//cloud_sql_proxy -instances=hallowed-trail-313100:us-central1:sgr-prototype=tcp:1433");
    }
    
    
}
