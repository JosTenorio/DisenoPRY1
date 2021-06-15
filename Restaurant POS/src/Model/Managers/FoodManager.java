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
import org.javatuples.Pair;


/**
 *
 * @author JOS
 */
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
    
    public static ArrayList<Pair<String,String>> getFoodByCategory(String categoryName, boolean includeMainDishes) {
        ResultSet rs;
        ArrayList<Pair<String,String>> results = new ArrayList<>();
        PreparedStatement getFoodByCathegoryStatement;
        if (!PreparedStatements.containsKey("getFoodByCathegoryStatement")){
            String sql = "SELECT Nombre, DireccionFoto FROM Comida WHERE IdCategoria = (SELECT Id FROM CategoriaCom WHERE nombre = ?) AND (1 = ? OR CantidadAcomp IS NULL)";
            try {
                getFoodByCathegoryStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getFoodByCathegoryStatement", getFoodByCathegoryStatement);
        } else {
            getFoodByCathegoryStatement = PreparedStatements.get("getFoodByCathegoryStatement");
        }
        try {
            getFoodByCathegoryStatement.setString(1, categoryName);
            if (includeMainDishes)
                getFoodByCathegoryStatement.setInt(2, 1);
            else
                getFoodByCathegoryStatement.setInt(2, 0);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        try {
            rs = getFoodByCathegoryStatement.executeQuery();
            while (rs.next()) {
                Pair<String,String> result;
                result = new Pair<>(rs.getString(1), rs.getString(2));
                results.add(result);
            }
            return results;
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return results;
        }
    }
    
    public static ArrayList<Pair<String,String>> getUncategorizedFood (boolean includeMainDishes) {
        ResultSet rs;
        ArrayList<Pair<String,String>> results = new ArrayList<>();
        PreparedStatement getUncategorizedFoodStatement;
        if (!PreparedStatements.containsKey("getUncategorizedFoodStatement")){
            String sql = "SELECT Nombre, DireccionFoto FROM Comida WHERE IdCategoria IS NULL AND (1 = ? OR CantidadAcomp IS NULL)";
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
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        try {
            rs = getUncategorizedFoodStatement.executeQuery();
            while (rs.next()) {
                Pair<String,String> result;
                result = new Pair<>(rs.getString(1), rs.getString(2));
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
