package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by Marc on 24/10/2015.
 */
public interface RespuestaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_RESPUESTA = "insert into respuesta (id, idtema, userid, content) values (UNHEX(?), unhex(?), unhex(?), ?)";
    public final static String GET_RESPUESTA_BY_ID = "select hex(r.id) as id,hex(r.idtema) as idtema, hex(r.userid) as userid, u.fullname, r.content, r.creation_timestamp, r.last_modified from respuesta r, users u where r.id=unhex(?) and u.id=r.userid";
    public final static String GET_RESPUESTA = "select hex(id) as id, hex(idtema) as idtema, hex(userid) as userid, content, creation_timestamp, last_modified from respuesta where idtema=unhex(?)";
    public final static String UPDATE_RESPUESTA = "update respuesta set content=? where id=unhex(?) ";
    public final static String DELETE_RESPUESTA = "delete from respuesta where id=unhex(?)";
    public final static String GET_RELACION = "select hex(r.id) as id,hex(r.grupoid) as grupoid, hex(r.userid) as userid from relaciongrupousuario  r where r.grupoid=unhex(?) and r.userid=unhex(?)";

}
