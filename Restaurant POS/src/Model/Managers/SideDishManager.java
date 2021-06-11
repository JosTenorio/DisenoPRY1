/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Managers;

import Connection.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    public static ArrayList<String> getSideDishNameByItem (int itemId) throws SQLException {
        ResultSet rs;
        ArrayList<String> results = new ArrayList<String>();
        PreparedStatement getSideDishNameByItemStatement;
        if (!PreparedStatements.containsKey("getSideDishNameByItemStatement")){
            String sql = "SELECT Comida.Nombre FROM Agregado INNER JOIN Comida ON Agregado.IdAcomp = Comida.Id WHERE IdItem = ?";
            getSideDishNameByItemStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("insertSideDishStatement", getSideDishNameByItemStatement);
        } else {
            getSideDishNameByItemStatement = PreparedStatements.get("getSideDishNameByItemStatement");
        }
        getSideDishNameByItemStatement.setInt(1, itemId);
        rs = getSideDishNameByItemStatement.executeQuery();
        while (rs.next()) {
           results.add(rs.getString(1));
        }
       return results;
    }
}
