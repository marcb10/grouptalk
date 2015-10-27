package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.ColeccionGrupo;
import edu.upc.eetac.dsa.grouptalk.entity.Grupo;

import java.sql.SQLException;

/**
 * Created by Marc on 24/10/2015.
 */
public interface GrupoDAO {
    public Grupo createGrupo(String nombre, String userid) throws SQLException;
    public Grupo getGrupoById(String id) throws SQLException;
    public ColeccionGrupo getGrupos() throws SQLException;
    public boolean deleteGrupo(String id) throws SQLException;
    public Grupo suscribirGrupo(String id, String idu) throws SQLException;
    public boolean dessuscribirGrupo(String id, String idu) throws SQLException;
}
