package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.ColeccionTema;
import edu.upc.eetac.dsa.grouptalk.entity.Tema;

import java.sql.SQLException;

/**
 * Created by Marc on 24/10/2015.
 */
public interface TemaDAO {
    public Tema createTema(String grupoid, String userid, String subject,String content) throws SQLException;
    public Tema getTemaById(String id) throws SQLException;
    public ColeccionTema getTemas() throws SQLException;
    public Tema updateTema(String id, String subject, String content) throws SQLException;
    public boolean deleteTema(String id) throws SQLException;
}
