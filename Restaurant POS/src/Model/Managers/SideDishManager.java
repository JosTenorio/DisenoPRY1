/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Managers;

import Connection.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;



public class SideDishManager {
        
    private static Hashtable<String, PreparedStatement> PreparedStatements = new Hashtable<String, PreparedStatement>();
    
    public static int insertSideDish (String sideDishName, int itemId) throws SQLException {
        int rowsAffected;
        PreparedStatement insertSideDishStatement;
        if (!PreparedStatements.containsKey("insertSideDishStatement")){
            String sql = "INSERT Agregado VALUES (?, (SELECT Comida.Id FROM Comida WHERE Comida.Nombre = ?))";
            insertSideDishStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("insertSideDishStatement", insertSideDishStatement);
        } else {
            insertSideDishStatement = PreparedStatements.get("insertSideDishStatement");
        }
        insertSideDishStatement.setInt(1, itemId);
        insertSideDishStatement.setString(2, sideDishName);
        rowsAffected = insertSideDishStatement.executeUpdate();
        return rowsAffected;
    }
    
}
