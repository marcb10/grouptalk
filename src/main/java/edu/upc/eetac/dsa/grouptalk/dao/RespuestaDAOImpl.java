package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.ColeccionRespuesta;
import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Marc on 24/10/2015.
 */
public class RespuestaDAOImpl implements RespuestaDAO {
    @Override
    public Respuesta createRespuesta(String idtema, String userid, String content) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(RespuestaDAOQuery.CREATE_RESPUESTA);
            stmt.setString(1, id);
            stmt.setString(2, idtema);
            stmt.setString(3, userid);
            stmt.setString(4, content);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getRespuestaById(id);
    }

    @Override
    public Respuesta getRespuestaById(String id) throws SQLException {
        Respuesta respuesta = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.GET_RESPUESTA_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                respuesta = new Respuesta();
                respuesta.setId(rs.getString("id"));
                respuesta.setIdtema(rs.getString("idtema"));
                respuesta.setUserid(rs.getString("userid"));
                respuesta.setCreator(rs.getString("fullname"));
                respuesta.setContent(rs.getString("content"));
                respuesta.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                respuesta.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return respuesta;
    }

    @Override
    public ColeccionRespuesta getRespuestas() throws SQLException {
        ColeccionRespuesta coleccionRespuesta = new ColeccionRespuesta();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RespuestaDAOQuery.GET_RESPUESTA);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Respuesta respuesta = new Respuesta();
                respuesta.setId(rs.getString("id"));
                respuesta.setIdtema(rs.getString("idtema"));
                respuesta.setUserid(rs.getString("userid"));
                respuesta.setContent(rs.getString("content"));
                respuesta.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                respuesta.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    coleccionRespuesta.setNewestTimestamp(respuesta.getLastModified());
                    first = false;
                }
                coleccionRespuesta.setOldestTimestamp(respuesta.getLastModified());
                coleccionRespuesta.getRespuestas().add(respuesta);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return coleccionRespuesta;
    }

    @Override
    public Respuesta updateRespuesta(String id, String content) throws SQLException {
        Respuesta respuesta = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.UPDATE_RESPUESTA);
            stmt.setString(1, content);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                respuesta = getRespuestaById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return respuesta;
    }

    @Override
    public boolean deleteRespuesta(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.DELETE_RESPUESTA);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();
            if (connection != null)
                connection.close();
        }
    }
}


