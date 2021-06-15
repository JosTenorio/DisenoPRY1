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
import org.javatuples.Triplet;

public class FoodManager {
    private static Hashtable<String, PreparedStatement> PreparedStatements = new Hashtable<String, PreparedStatement>();
    private static String error;
    private static Boolean errorFlag;
    
    public static int insertFood (String name, String description, double prize, String photoDir, boolean isArchived, boolean isSideDish, int sideDishAmount) {
        int rowsAffected = -1;
        PreparedStatement insertFoodStatement;
        if (!PreparedStatements.containsKey("insertFoodStatement")){
            String sql = "INSERT Comida VALUES (?, ?, ?, ?, ?, ?, null)";
            try {
                insertFoodStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("insertFoodStatement", insertFoodStatement);
        } else {
            insertFoodStatement = PreparedStatements.get("insertTableStatement");
        }
        try {
            insertFoodStatement.setString(1, description);
            insertFoodStatement.setString(2, photoDir);
            insertFoodStatement.setDouble(3, prize);
            insertFoodStatement.setString(4, name);
            insertFoodStatement.setBoolean(5, isArchived);
            if (isSideDish)
                insertFoodStatement.setNull(6, java.sql.Types.INTEGER);
            else
                insertFoodStatement.setInt(6, sideDishAmount);               

        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = insertFoodStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }
    
    
    public static int toggleFood (String foodName) {
        int rowsAffected = -1;
        PreparedStatement toggleFoodStatement;
        if (!PreparedStatements.containsKey("toggleFoodStatement")){
            String sql = "UPDATE Comida SET Archivado = Archivado ^ 1 WHERE Nombre = ?";
            try {
                toggleFoodStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("toggleFoodStatement", toggleFoodStatement);
        } else {
            toggleFoodStatement = PreparedStatements.get("toggleFoodStatement");
        }
        try {
            toggleFoodStatement.setString(1, foodName);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = toggleFoodStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }
    
    public static ArrayList<Triplet<String,String,Boolean>> getUncategorizedFood (boolean includeMainDishes, boolean includeArchived) {
        ResultSet rs;
        ArrayList<Triplet<String,String,Boolean>> results = new ArrayList<>();
        PreparedStatement getUncategorizedFoodStatement;
        if (!PreparedStatements.containsKey("getUncategorizedFoodStatement")){
            String sql = "SELECT Nombre, DireccionFoto, Archivado FROM Comida WHERE IdCategoria IS NULL AND (1 = ? OR CantidadAcomp IS NULL) AND (1 = ? OR Archivado = 0)";
            try {
                getUncategorizedFoodStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getUncategorizedFoodStatement", getUncategorizedFoodStatement);
        } else {
            getUncategorizedFoodStatement = PreparedStatements.get("getUncategorizedFoodStatement");
        }
        try {
            if (includeMainDishes)
                getUncategorizedFoodStatement.setInt(1, 1);
            else
                getUncategorizedFoodStatement.setInt(1, 0);
            if (includeArchived)
                getUncategorizedFoodStatement.setInt(2, 1);
            else
                getUncategorizedFoodStatement.setInt(2, 0);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        try {
            rs = getUncategorizedFoodStatement.executeQuery();
            while (rs.next()) {
                Triplet<String,String,Boolean> result;
                result = new Triplet<>(rs.getString(1), rs.getString(2), rs.getBoolean(3));
                results.add(result);
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
