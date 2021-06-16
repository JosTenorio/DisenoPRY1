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
import java.util.ArrayList;
import org.javatuples.Septet;


public class ItemManager {
        
    private static Hashtable<String, PreparedStatement> PreparedStatements = new Hashtable<String, PreparedStatement>();
    private static String error;
    private static Boolean errorFlag;
    
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
    
    private static ArrayList<Septet<Integer,String,Double,Integer,String,Boolean,Boolean>> selectItemsByOrder (int orderId) throws SQLException {
        ResultSet rs;
        ArrayList<Septet<Integer,String,Double,Integer,String,Boolean,Boolean>> results = new ArrayList<>();
        PreparedStatement selectItemsByOrderStatement;
        if (!PreparedStatements.containsKey("selectItemsByOrderStatement")){
            String sql = "SELECT Item.Id, Comida.Nombre, Comida.Precio, Item.Estado, Item.Nota, Item.Pago, Comida.CantidadAcomp FROM Item INNER JOIN Comida ON Comida.Id = Item.IdComida WHERE Item.IdOrden = ?";
            selectItemsByOrderStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("selectItemsByOrderStatement", selectItemsByOrderStatement);
        } else {
            selectItemsByOrderStatement = PreparedStatements.get("selectItemsByOrderStatement");
        }
        selectItemsByOrderStatement.setInt(1, orderId);
        rs = selectItemsByOrderStatement.executeQuery();
        while (rs.next()) {
           Septet<Integer,String,Double,Integer,String,Boolean,Boolean> result;
           rs.getInt(7);
           boolean isMainDish = (!rs.wasNull());
           result = new Septet<>(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getBoolean(6), isMainDish);
           results.add(result);
        }
        return results;
    }
    
    public static ArrayList<Item> getItemsByOrder (int orderId, boolean includePaidItems) throws SQLException {
        ArrayList<Item> results = new ArrayList<>();
        ArrayList<Septet<Integer,String,Double,Integer,String,Boolean,Boolean>> selectResults;
        selectResults = selectItemsByOrder(orderId);
        for (Septet<Integer,String,Double,Integer,String,Boolean,Boolean> result : selectResults) {
            ArrayList<String> sideDishes;
            if (includePaidItems || !result.getValue5()) {
                if(result.getValue6()){
                    sideDishes = SideDishManager.getSideDishNameByItem(result.getValue0());
                }
                else {
                    sideDishes = null;
                }
                Item item = new Item(result.getValue0(), result.getValue1(),sideDishes,result.getValue2(),result.getValue3(),result.getValue4());
                results.add(item);
            }
        }
        return results;
    }
    
    public static ArrayList<Item> getUnreadyItemsByOrder (int orderId) throws SQLException {
        ArrayList<Item> results = new ArrayList<>();
        ArrayList<Septet<Integer,String,Double,Integer,String,Boolean,Boolean>> selectResults;
        selectResults = selectItemsByOrder(orderId);
        for (Septet<Integer,String,Double,Integer,String,Boolean,Boolean> result : selectResults) {
            ArrayList<String> sideDishes;
            if (result.getValue3()==0) {
                if(result.getValue6()){
                    sideDishes = SideDishManager.getSideDishNameByItem(result.getValue0());
                }
                else {
                    sideDishes = null;
                }
                Item item = new Item(result.getValue0(), result.getValue1(),sideDishes,result.getValue2(),result.getValue3(),result.getValue4());
                results.add(item);
            }
        }
        return results;
    }
    
    public static ArrayList<Item> getReadyItemsByOrder (int orderId) throws SQLException {
        ArrayList<Item> results = new ArrayList<>();
        ArrayList<Septet<Integer,String,Double,Integer,String,Boolean,Boolean>> selectResults;
        selectResults = selectItemsByOrder(orderId);
        for (Septet<Integer,String,Double,Integer,String,Boolean,Boolean> result : selectResults) {
            ArrayList<String> sideDishes;
            if (result.getValue3()==1) {
                if(result.getValue6()){
                    sideDishes = SideDishManager.getSideDishNameByItem(result.getValue0());
                }
                else {
                    sideDishes = null;
                }
                Item item = new Item(result.getValue0(), result.getValue1(),sideDishes,result.getValue2(),result.getValue3(),result.getValue4());
                results.add(item);
            }
        }
        return results;
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
    
    
    public static int markReadyItem (int itemId) {
        int rowsAffected = -1;
        PreparedStatement markReadyItemStatement;
        if (!PreparedStatements.containsKey("markReadyItemStatement")){
            String sql = "UPDATE Item SET Estado = 1 WHERE Id = ?";
            try {
                markReadyItemStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("markReadyItemStatement", markReadyItemStatement);
        } else {
            markReadyItemStatement = PreparedStatements.get("markReadyItemStatement");
        }
        try {
            markReadyItemStatement.setInt(1, itemId);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = markReadyItemStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }
    
    public static int markDeliveredItem (int itemId) {
        int rowsAffected = -1;
        PreparedStatement markDeliveredItemStatement;
        if (!PreparedStatements.containsKey("markDeliveredItemStatement")){
            String sql = "UPDATE Item SET Estado = 2 WHERE Id = ?";
            try {
                markDeliveredItemStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("markDeliveredItemStatement", markDeliveredItemStatement);
        } else {
            markDeliveredItemStatement = PreparedStatements.get("markDeliveredItemStatement");
        }
        try {
            markDeliveredItemStatement.setInt(1, itemId);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = markDeliveredItemStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }
    
    public static int markPaidItem (int itemId) {
        int rowsAffected = -1;
        PreparedStatement markPaidItemStatement;
        if (!PreparedStatements.containsKey("markPaidItemStatement")){
            String sql = "UPDATE Item SET Pago = 1 WHERE Id = ?";
            try {
                markPaidItemStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("markPaidItemStatement", markPaidItemStatement);
        } else {
            markPaidItemStatement = PreparedStatements.get("markPaidItemStatement");
        }
        try {
            markPaidItemStatement.setInt(1, itemId);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = markPaidItemStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }


}
