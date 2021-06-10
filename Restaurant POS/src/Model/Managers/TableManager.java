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
import java.util.logging.Level;
import java.util.logging.Logger;


public class TableManager {
    
    private static Hashtable<String, PreparedStatement> PreparedStatements = new Hashtable<String, PreparedStatement>();
    private static String error;
    private static Boolean errorFlag;

    
    public static int insertTable (String name, double orientation, int shape, Double sizeA, Double sizeB, Double posX, Double posY) {
        int rowsAffected = -1;
        PreparedStatement insertTableStatement;
        if (!PreparedStatements.containsKey("insertTableStatement")){
            String sql = "INSERT Mesa VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                insertTableStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("insertTableStatement", insertTableStatement);
        } else {
            insertTableStatement = PreparedStatements.get("insertTableStatement");
        }
        try {
            insertTableStatement.setDouble(1, orientation);
            insertTableStatement.setDouble(2, shape);
            insertTableStatement.setString(3, name);
            insertTableStatement.setDouble(4, sizeA);
            insertTableStatement.setDouble(5, sizeB);
            insertTableStatement.setDouble(6, posX);
            insertTableStatement.setDouble(7, posY);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = insertTableStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }
    
    public static ArrayList<String> getTableNames() {
        ResultSet rs;
        ArrayList results = new ArrayList<>();
        PreparedStatement selectTableNamesStatement;
        if (!PreparedStatements.containsKey("selectTableNamesStatement")){
            String sql = "SELECT nombre FROM Mesa";
            try {
                selectTableNamesStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("selectTableNamesStatement", selectTableNamesStatement);
        } else {
            selectTableNamesStatement = PreparedStatements.get("selectTableNamesStatement");
        }
        try {
            rs = selectTableNamesStatement.executeQuery();
            while (rs.next()) {
                results.add(rs.getString(1));
            }
            return results;
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return results;
        }
    }
    
    
    
    
    
    public static boolean hasError() {
        return errorFlag;
    }
    
    public static String getErrorMessage() {
        return error;
    }
     
}
