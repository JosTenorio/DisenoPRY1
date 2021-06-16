package Model.Managers;

import Connection.ConnectionManager;
import Model.Ingredient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javatuples.Triplet;


public class IngredientManager {
    
    
    private static Hashtable<String, PreparedStatement> PreparedStatements = new Hashtable<String, PreparedStatement>();
    private static String error;
    private static Boolean errorFlag;
    
    
    public static int insertIngredient (Ingredient ing) {
        int rowsAffected = -1;
        PreparedStatement insertIngredientStatement;
        if (!PreparedStatements.containsKey("insertIngredientStatement")){
            String sql = "INSERT Ingrediente VALUES (?, ?, ?, ?, ?, ?, null)";
            try {
                insertIngredientStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("insertIngredientStatement", insertIngredientStatement);
        } else {
            insertIngredientStatement = PreparedStatements.get("insertIngredientStatement");
        }
        try {
            insertIngredientStatement.setString(1, ing.name);
            insertIngredientStatement.setString(2, ing.description);
            insertIngredientStatement.setDouble(3, ing.minimum);
            insertIngredientStatement.setDouble(4, ing.quantity);
            insertIngredientStatement.setString(5, ing.unit);
            insertIngredientStatement.setString(6, ing.imgPath);             
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = insertIngredientStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }
    

    public static int updateIngredient (String oldName, Ingredient ing) {
        int rowsAffected = -1;
        PreparedStatement updateIngredientStatement;
        if (!PreparedStatements.containsKey("updateIngredientStatement")){
            String sql = "UPDATE Ingrediente SET Nombre = ?, Descripcion = ?, Minimo = ?, Cantidad = ?, Medida = ?, DireccionFoto = ? WHERE Nombre = ?";
            try {
                updateIngredientStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return rowsAffected;
            }
            PreparedStatements.put("updateIngredientStatement", updateIngredientStatement);
        } else {
            updateIngredientStatement = PreparedStatements.get("updateIngredientStatement");
        }
        try {
            updateIngredientStatement.setString(1, ing.name);
            updateIngredientStatement.setString(2, ing.description);
            updateIngredientStatement.setDouble(3, ing.minimum);
            updateIngredientStatement.setDouble(4, ing.quantity);
            updateIngredientStatement.setString(5, ing.unit);
            updateIngredientStatement.setString(6, ing.imgPath);
            updateIngredientStatement.setString(7, oldName);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return rowsAffected;
        }
        try {
            rowsAffected = updateIngredientStatement.executeUpdate();
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return rowsAffected;
        }
        return rowsAffected;
    }
    
    public static Ingredient getIngredientDetails (String foodName) {
        ResultSet rs;
        Ingredient results = null;
        PreparedStatement getIngredientDetailsStatement;
        if (!PreparedStatements.containsKey("getIngredientDetailsStatement")){
            String sql = "SELECT Nombre, Descripcion, Minimo, Cantidad, Medida, DireccionFoto FROM Ingrediente WHERE Nombre = ?";
            try {
                getIngredientDetailsStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getIngredientDetailsStatement", getIngredientDetailsStatement);
        } else {
            getIngredientDetailsStatement = PreparedStatements.get("getIngredientDetailsStatement");
        }
        try {
            getIngredientDetailsStatement.setString(1, foodName);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        try {
            rs = getIngredientDetailsStatement.executeQuery();
            if (rs.next()) {
                results = new Ingredient (rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getString(5), rs.getString(6));
            }
            return results;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            errorFlag = true;
            error = ex.getMessage();
            return results;
        }
    }
        
    public static ArrayList<Triplet<String,String,Boolean>> getIngredientByCategory(String categoryName) {
        ResultSet rs;
        ArrayList<Triplet<String,String,Boolean>> results = new ArrayList<>();
        PreparedStatement getIngredientByCategoryStatement;
        if (!PreparedStatements.containsKey("getIngredientByCategoryStatement")){
            String sql = "SELECT Nombre, DireccionFoto FROM Ingrediente WHERE IdCategoria = (SELECT Id FROM CategoriaIng WHERE Nombre = ?)";
            try {
                getIngredientByCategoryStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getIngredientByCategoryStatement", getIngredientByCategoryStatement);
        } else {
            getIngredientByCategoryStatement = PreparedStatements.get("getIngredientByCategoryStatement");
        }
        try {
            getIngredientByCategoryStatement.setString (1, categoryName);

        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        try {
            rs = getIngredientByCategoryStatement.executeQuery();
            while (rs.next()) {
                Triplet<String,String,Boolean> result;
                result = new Triplet<>(rs.getString(1), rs.getString(2), false);
                results.add(result);
            }
            return results;
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return results;
        }
    }
    
    public static ArrayList<Triplet<String,String,Boolean>> getUncategorizedIngs () {
        ResultSet rs;
        ArrayList<Triplet<String,String,Boolean>> results = new ArrayList<>();
        PreparedStatement getUncategorizedIngsStatement;
        if (!PreparedStatements.containsKey("getUncategorizedIngsStatement")){
            String sql = "SELECT Nombre, DireccionFoto FROM Ingrediente WHERE IdCategoria IS NULL";
            try {
                getUncategorizedIngsStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getUncategorizedIngsStatement", getUncategorizedIngsStatement);
        } else {
            getUncategorizedIngsStatement = PreparedStatements.get("getUncategorizedIngsStatement");
        }
        try {
            rs = getUncategorizedIngsStatement.executeQuery();
            while (rs.next()) {
                Triplet<String,String,Boolean> result;
                result = new Triplet<>(rs.getString(1), rs.getString(2), false);
                results.add(result);
            }
            return results;
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return results;
        }
    }
        
    
    
}
