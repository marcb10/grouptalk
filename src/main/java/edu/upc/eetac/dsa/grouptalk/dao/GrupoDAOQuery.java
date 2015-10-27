package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by Marc on 24/10/2015.
 */
public interface GrupoDAOQuery {
        public final static String UUID = "select REPLACE(UUID(),'-','')";
        public final static String CREATE_GRUPO = "insert into grupo (id, userid, nombre) values (UNHEX(?), UNHEX(?), ?)";
        public final static String GET_GRUPO_BY_ID = "select hex(g.id) as id, hex(g.userid) as userid, g.nombre, g.creation_timestamp, g.last_modified from grupo g, users u where g.id=unhex(?) and u.id=g.userid";
        public final static String GET_GRUPOS = "select hex(id) as id, hex(userid) as userid, nombre, creation_timestamp, last_modified from grupo";
        public final static String DELETE_GRUPO = "delete from grupo where id=unhex(?)";
        public final static String DES_GRUPO = "delete from relaciongrupousuario where grupoid=unhex(?) and userid=unhex(?)";
        public final static String SUBS_GRUPO = "insert into relaciongrupousuario (id, grupoid, userid) values (UNHEX(?), UNHEX(?), UNHEX(?))";
}
