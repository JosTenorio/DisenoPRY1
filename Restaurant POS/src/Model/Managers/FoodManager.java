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
    
    public static boolean hasError() {
        return errorFlag;
    }
    
    public static String getErrorMessage() {
        return error;
    }
     
    
    
}
