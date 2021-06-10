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
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Item;


public class ItemManager {
        
    private static Hashtable<String, PreparedStatement> PreparedStatements = new Hashtable<String, PreparedStatement>();
    
    private static int createItem (String foodName, int orderId, String Notes) throws SQLException {
        int rowsAffected;
        PreparedStatement createItemStatement;
        if (!PreparedStatements.containsKey("createItemStatement")){
            String sql = "INSERT INTO Item (IdOrden, IdComida, Nota) VALUES (?, (SELECT Comida.Id FROM Comida WHERE Comida.Nombre = ?), ?)";
            createItemStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("createItemStatement", createItemStatement);
        } else {
            createItemStatement = PreparedStatements.get("createItemStatement");
        }
        createItemStatement.setInt(1, orderId);
        createItemStatement.setString(2, foodName);
        createItemStatement.setString(3, Notes);
        rowsAffected = createItemStatement.executeUpdate();
        return rowsAffected;
    }
    
    public static void insertItem (Item item, int orderId) throws SQLException {
        if (item.sideDishes == null) {
            try {
                createItem(item.name,orderId ,item.notes);
            } catch (SQLException ex) {
                Logger.getLogger(ItemManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            createItem(item.name,orderId ,item.notes);
            int itemId = getLastItemId();
            for (String sideDish : item.sideDishes) { 		      
                SideDishManager.insertSideDish(sideDish, itemId);
            }
        }               
    }
    
    private static int getLastItemId() throws SQLException {
        ResultSet rs;
        int results;
        PreparedStatement getLastItemIdStatement;
        if (!PreparedStatements.containsKey("getLastItemIdStatement")){
            String sql = "SELECT MAX(Id) FROM Item";
            getLastItemIdStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("getLastItemIdStatement", getLastItemIdStatement);
        } else {
            getLastItemIdStatement = PreparedStatements.get("getLastItemIdStatement");
        }
        rs = getLastItemIdStatement.executeQuery();
        rs.next();
        results = rs.getInt(1);
        return results;
    }
}
