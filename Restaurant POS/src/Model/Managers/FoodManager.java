/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Managers;

import Connection.ConnectionManager;
import Model.Dish;
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
    
    public static int insertFood (Dish dish) {
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
            insertFoodStatement.setString(1, dish.description);
            insertFoodStatement.setString(2, dish.imgPath);
            insertFoodStatement.setDouble(3, dish.price);
            insertFoodStatement.setString(4, dish.name);
            insertFoodStatement.setBoolean(5, false);
            if (dish.isSideDish)
                insertFoodStatement.setNull(6, java.sql.Types.INTEGER);
            else
                insertFoodStatement.setInt(6, dish.sideDishes);               

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
    
    public static int categorizeFood (String foodName, String categoryName) {
        int rowsAffected = -1;
        PreparedStatement categorizeFoodStatement;
        if (!PreparedStatements.containsKey("categorizeFoodStatement")){
            String sql = "UPDATE Comida SET IdCategoria = (SELECT Id FROM CategoriaCom WHERE Nombre = ?) WHERE Nombre = ?";
            try {
                categorizeFoodStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("categorizeFoodStatement", categorizeFoodStatement);
        } else {
            categorizeFoodStatement = PreparedStatements.get("categorizeFoodStatement");
        }
        try {
            if (categoryName == null)
                categorizeFoodStatement.setNull(1, java.sql.Types.NVARCHAR);
            else
                categorizeFoodStatement.setString(1, categoryName);
            categorizeFoodStatement.setString(2, foodName);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = categorizeFoodStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }
    
    public static ArrayList<Triplet<String,String,Boolean>> getFoodByCategory(String categoryName, boolean includeMainDishes, boolean includeArchived) {
        ResultSet rs;
        ArrayList<Triplet<String,String,Boolean>> results = new ArrayList<>();
        PreparedStatement getFoodByCategoryStatement;
        if (!PreparedStatements.containsKey("getFoodByCategoryStatement")){
            String sql = "SELECT Nombre, DireccionFoto, Archivado FROM Comida WHERE IdCategoria = (SELECT Id FROM CategoriaCom WHERE nombre = ?) AND (1 = ? OR CantidadAcomp IS NULL) AND (1 = ? OR Archivado = 0)";
            try {
                getFoodByCategoryStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getFoodByCategoryStatement", getFoodByCategoryStatement);
        } else {
            getFoodByCategoryStatement = PreparedStatements.get("getFoodByCategoryStatement");
        }
        try {
            getFoodByCategoryStatement.setString(1, categoryName);
            if (includeMainDishes)
                getFoodByCategoryStatement.setInt(2, 1);
            else
                getFoodByCategoryStatement.setInt(2, 0);
            if (includeArchived)
                getFoodByCategoryStatement.setInt(3, 1);
            else
                getFoodByCategoryStatement.setInt(3, 0);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        try {
            rs = getFoodByCategoryStatement.executeQuery();
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
    
    public static ArrayList<Triplet<String,String,Boolean>> getFoodByCategory(String categoryName, boolean includeMainDishes, boolean includeArchived) {
        ResultSet rs;
        ArrayList<Triplet<String,String,Boolean>> results = new ArrayList<>();
        PreparedStatement getFoodByCategoryStatement;
        if (!PreparedStatements.containsKey("getFoodByCategoryStatement")){
            String sql = "SELECT Nombre, DireccionFoto, Archivado FROM Comida WHERE IdCategoria = (SELECT Id FROM CategoriaCom WHERE nombre = ?) AND (1 = ? OR CantidadAcomp IS NULL) AND (1 = ? OR Archivado = 0)";
            try {
                getFoodByCategoryStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getFoodByCategoryStatement", getFoodByCategoryStatement);
        } else {
            getFoodByCategoryStatement = PreparedStatements.get("getFoodByCategoryStatement");
        }
        try {
            getFoodByCategoryStatement.setString(1, categoryName);
            if (includeMainDishes)
                getFoodByCategoryStatement.setInt(2, 1);
            else
                getFoodByCategoryStatement.setInt(2, 0);
            if (includeArchived)
                getFoodByCategoryStatement.setInt(3, 1);
            else
                getFoodByCategoryStatement.setInt(3, 0);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        try {
            rs = getFoodByCategoryStatement.executeQuery();
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
