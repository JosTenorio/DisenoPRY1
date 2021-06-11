/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Managers;

import Connection.ConnectionManager;
import Model.Item;
import Model.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrderManager {
    
    private static Hashtable<String, PreparedStatement> PreparedStatements = new Hashtable<String, PreparedStatement>();
    private static String error;
    private static Boolean errorFlag;
    
 
    private static int createOrder (String tableName) throws SQLException {
        int rowsAffected;
        PreparedStatement createOrderStatement;
        if (!PreparedStatements.containsKey("createOrderStatement")){
            String sql = "INSERT INTO Orden(IdMesa) VALUES ((SELECT Id FROM Mesa WHERE Nombre = ?))";
            createOrderStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("createOrderStatement", createOrderStatement);
        } else {
            createOrderStatement = PreparedStatements.get("createOrderStatement");
        }
        createOrderStatement.setString(1, tableName);
        rowsAffected = createOrderStatement.executeUpdate();
        return rowsAffected;
    }
    
    private static int getLastOrderId() throws SQLException {
        ResultSet rs;
        int results;
        PreparedStatement getLastOrderIdStatement;
        if (!PreparedStatements.containsKey("getLastOrderIdStatement")){
            String sql = "SELECT MAX(Id) FROM Orden";
            getLastOrderIdStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("getLastOrderIdStatement", getLastOrderIdStatement);
        } else {
            getLastOrderIdStatement = PreparedStatements.get("getLastOrderIdStatement");
        }
        rs = getLastOrderIdStatement.executeQuery();
        rs.next();
        results = rs.getInt(1);
        return results;
    }
    
    public static int deleteOrderById(int orderId){
        int affectedRows = -1;
        PreparedStatement deleteOrderByIdStatement;
        try {
            if (!PreparedStatements.containsKey("deleteOrderByIdStatement")){
                String sql = "DELETE FROM Orden WHERE Id = ?";
                deleteOrderByIdStatement = ConnectionManager.getConnection().prepareStatement(sql);
                PreparedStatements.put("deleteOrderByIdStatement", deleteOrderByIdStatement);
            } else {
                deleteOrderByIdStatement = PreparedStatements.get("deleteOrderByIdStatement");
            }
            deleteOrderByIdStatement.setInt(1, orderId);
            affectedRows = deleteOrderByIdStatement.executeUpdate();
            return affectedRows;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return affectedRows;
        }
    }

    public static int insertOrder (Order order){
        int affectedRows = -1;
        int orderId;
        try {
            affectedRows = createOrder (order.tableName);
            orderId = getLastOrderId();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return affectedRows;
        }
        for (Item item : order.items) { 		      
            try {
                ItemManager.insertItem(item, orderId);
            } catch (SQLException ex) {
                errorFlag = true;
                error = ex.getMessage();
                deleteOrderById(orderId);
                return affectedRows;
            }
        }
        return affectedRows; 
    }
    
    public static Order getTableOrders(String tableName, boolean includePaidItems) {
        Order result = null;
        ArrayList<Item> tableItems = new ArrayList<>();
        ArrayList<Integer> OrderIds;
        try {
            OrderIds = getOrderIdsByTableName(tableName);
        } catch (SQLException ex) {
            errorFlag = true;
            error = "Esto no debería pasar";
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }
        for (Integer orderId : OrderIds) {
            ArrayList<Item> items;
            try {
                items = ItemManager.getItemsByOrder(orderId, includePaidItems);
            } catch (SQLException ex) {
                errorFlag = true;
                error = "Esto no debería pasar";
                Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
                return result;
            }
            items.forEach(item -> {
                tableItems.add(item);
            });
        }
        result = new Order (tableName, tableItems);
        return result;
    }
    
    private static ArrayList<Integer> getOrderIdsByTableName(String tableName) throws SQLException {
        ResultSet rs;
        ArrayList<Integer> results = new ArrayList<>();
        PreparedStatement getOrderIdsByTableNameStatement;
        if (!PreparedStatements.containsKey("getOrderIdsByTableNameStatement")){
            String sql = "SELECT Orden.Id FROM Orden INNER JOIN Mesa ON Mesa.Id = Orden.IdMesa WHERE Mesa.Nombre = ?";
            getOrderIdsByTableNameStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("getOrderIdsByTableNameStatement", getOrderIdsByTableNameStatement);
        } else {
            getOrderIdsByTableNameStatement = PreparedStatements.get("getOrderIdsByTableNameStatement");
        }
        getOrderIdsByTableNameStatement.setString(1, tableName);
        rs = getOrderIdsByTableNameStatement.executeQuery();
       while (rs.next()) {
           results.add(rs.getInt(1));
       }
       return results;
    }
    
    public static boolean hasError() {
        return errorFlag;
    }
    
    public static String getErrorMessage() {
        return error;
    }

}
