package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.ColeccionRespuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RelacionGrupoUsuario;
import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;

import java.sql.SQLException;

/**
 * Created by Marc on 24/10/2015.
 */
public interface RespuestaDAO {
    public Respuesta createRespuesta(String idtema, String userid, String content) throws SQLException;
    public Respuesta getRespuestaById(String id) throws SQLException;
    public ColeccionRespuesta getRespuestas(String id) throws SQLException;
    public Respuesta updateRespuesta(String id, String content) throws SQLException;
    public boolean deleteRespuesta(String id) throws SQLException;
    public RelacionGrupoUsuario getRelacion(String id, String idu) throws SQLException;

}
