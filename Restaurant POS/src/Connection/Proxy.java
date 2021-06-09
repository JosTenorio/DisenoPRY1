/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import com.profesorfalken.jpowershell.*;

public class Proxy {
    private static String proxyInitCommand;
    private static PowerShell PowerShellSession;
    
    
    public static void setProxyInit (String initCommand) {
        proxyInitCommand = initCommand;
    }
    
    private static void openPSSession () {
        if (PowerShellSession == null)
            PowerShellSession = PowerShell.openSession();
    }
    
    public static void closeProxy () {
        if (PowerShellSession != null)
            PowerShellSession.close();
    }
    
    public static void openProxy () {
        if (proxyInitCommand == null)
            return;
        openPSSession();
        PowerShellResponse response = PowerShellSession.executeCommand(proxyInitCommand);
    }
    
    
}
