package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by Marc on 24/10/2015.
 */
public interface GrupoDAOQuery {
        public final static String UUID = "select REPLACE(UUID(),'-','')";
        public final static String CREATE_GRUPO = "insert into stings (id, nombre, userid) values (UNHEX(?), ?, unhex(?))";
        public final static String GET_GRUPO_BY_ID = "select hex(s.id) as id, g.nombre, hex(g.userid) as userid, g.creation_timestamp, g.last_modified from grupo g, users u where g.id=unhex(?) and u.id=g.userid";
        public final static String GET_GRUPOS = "select hex(id) as id, nombre, hex(userid) as userid, creation_timestamp, last_modified from grupo";
        public final static String DELETE_GRUPO = "delete from grupo where id=unhex(?)";
}
