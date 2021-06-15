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


public class IngredientCtgrManager {
    
    private static Hashtable<String, PreparedStatement> PreparedStatements = new Hashtable<String, PreparedStatement>();
    private static String error;
    private static Boolean errorFlag;
    
    public static ArrayList<Pair<String,String>> getFatherCategories() {
        ResultSet rs;
        ArrayList<Pair<String,String>> results = new ArrayList<>();
        PreparedStatement getFatherCategoriesStatement;
        if (!PreparedStatements.containsKey("getFatherCategoriesStatement")){
            String sql = "SELECT nombre FROM CategoriaIng WHERE IdCategoriaPadre IS NULL";
            try {
                getFatherCategoriesStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getFatherCategoriesStatement", getFatherCategoriesStatement);
        } else {
            getFatherCategoriesStatement = PreparedStatements.get("getFatherCategoriesStatement");
        }
        try {
            rs = getFatherCategoriesStatement.executeQuery();
            while (rs.next()) {
                Pair<String,String> result;
                result = new Pair<>(rs.getString(1), getImageForCathegory(rs.getString(1)));
                results.add(result);
            }
            return results;
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return results;
        }
    }
    
    public static ArrayList<Pair<String,String>> getSubCategories(String cathegoryName) {
        ResultSet rs;
        ArrayList<Pair<String,String>> results = new ArrayList<>();
        PreparedStatement getSubCategoriesStatement;
        if (!PreparedStatements.containsKey("getSubCategoriesStatement")){
            String sql = "SELECT nombre FROM CategoriaCom WHERE IdCategoriaPadre = (SELECT Id FROM CategoriaCom WHERE nombre = ?) ";
            try {
                getSubCategoriesStatement = ConnectionManager.getConnection().prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
                errorFlag = true;
                return results;
            }
            PreparedStatements.put("getSubCategoriesStatement", getSubCategoriesStatement);
        } else {
            getSubCategoriesStatement = PreparedStatements.get("getSubCategoriesStatement");
        }
        try {
            getSubCategoriesStatement.setString(1, cathegoryName);
        } catch (SQLException ex) {
            errorFlag = true;
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        try {
            rs = getSubCategoriesStatement.executeQuery();
            while (rs.next()) {
                Pair<String,String> result;
                result = new Pair<>(rs.getString(1), getImageForCathegory(rs.getString(1)));
                results.add(result);
            }
            return results;
        } catch (SQLException ex) {
            errorFlag = true;
            error = ex.getMessage();
            return results;
        }
    }
    
    private static String getImageForCathegory(String cathegoryName) throws SQLException {
        ResultSet rs;
        String results = "";
        PreparedStatement getImageForCathegoryStatement;
        if (!PreparedStatements.containsKey("getImageForCathegoryStatement")){
            String sql = "WITH cte AS \n" +
                         "(\n" +
                         "  SELECT t1.Id\n" +
                         "  FROM CategoriaCom t1\n" +
                         "  WHERE nombre = ?\n" +
                         "  UNION ALL\n" +
                         "  SELECT t2.Id\n" +
                         "  FROM CategoriaCom t2 INNER JOIN cte c ON t2.IdCategoriaPadre = c.id\n" +
                         ")\n" +
                         "SELECT TOP 1 Comida.DireccionFoto\n" +
                         "FROM Comida\n" +
                         "WHERE Comida.IdCategoria IN (SELECT Id FROM cte)";
            getImageForCathegoryStatement = ConnectionManager.getConnection().prepareStatement(sql);
            PreparedStatements.put("getImageForCathegoryStatement", getImageForCathegoryStatement);        
        } else {
            getImageForCathegoryStatement = PreparedStatements.get("getImageForCathegoryStatement");
        }
        getImageForCathegoryStatement.setString(1, cathegoryName);
        rs = getImageForCathegoryStatement.executeQuery();
        if(rs.next())
            results = rs.getString(1);
        return results;
        }
    }
   
