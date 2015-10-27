package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.ColeccionTema;
import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.RelacionGrupoUsuario;
import edu.upc.eetac.dsa.grouptalk.entity.Tema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Marc on 24/10/2015.
 */
public class TemaDAOImpl implements TemaDAO {
    @Override
    public Tema createTema(String grupoid, String userid, String subject, String content) throws SQLException {
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

            stmt = connection.prepareStatement(TemaDAOQuery.CREATE_TEMA);
            stmt.setString(1, id);
            stmt.setString(2, grupoid);
            stmt.setString(3, userid);
            stmt.setString(4, subject);
            stmt.setString(5, content);
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
        return getTemaById(id);
    }

    @Override
    public Tema getTemaById(String id) throws SQLException {
        Tema tema = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.GET_TEMA_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tema = new Tema();
                tema.setId(rs.getString("id"));
                tema.setGrupoid(rs.getString("grupoid"));
                tema.setUserid(rs.getString("userid"));
                tema.setCreator(rs.getString("fullname"));
                tema.setSubject(rs.getString("subject"));
                tema.setContent(rs.getString("content"));
                tema.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                tema.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return tema;
    }

    @Override
    public ColeccionTema getTemas(String id) throws SQLException {
        ColeccionTema coleccionTema = new ColeccionTema();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(TemaDAOQuery.GET_TEMAS);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Tema tema = new Tema();
                tema.setId(rs.getString("id"));
                tema.setGrupoid(rs.getString("grupoid"));
                tema.setUserid(rs.getString("userid"));
                tema.setSubject(rs.getString("subject"));
                tema.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                tema.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    coleccionTema.setNewestTimestamp(tema.getLastModified());
                    first = false;
                }
                coleccionTema.setOldestTimestamp(tema.getLastModified());
                coleccionTema.getTemas().add(tema);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return coleccionTema;
    }

    @Override
    public Tema updateTema(String id, String subject, String content) throws SQLException {
        Tema tema = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.UPDATE_TEMA);
            stmt.setString(1, subject);
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                tema = getTemaById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return tema;
    }

    @Override
    public boolean deleteTema(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.DELETE_TEMA);
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

    @Override
    public RelacionGrupoUsuario getRelacion(String id, String idu) throws SQLException {
        RelacionGrupoUsuario relacionGrupoUsuario = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.GET_RELACION);
            stmt.setString(1, id);
            stmt.setString(2, idu);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                relacionGrupoUsuario = new RelacionGrupoUsuario();
                relacionGrupoUsuario.setId(rs.getString("id"));
                relacionGrupoUsuario.setGrupoid(rs.getString("grupoid"));
                relacionGrupoUsuario.setUserid(rs.getString("userid"));
            }
        } catch (SQLException e){
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return relacionGrupoUsuario;
    }

}
