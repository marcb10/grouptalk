package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by Marc on 24/10/2015.
 */
public interface TemaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_TEMA = "insert into tema (id, grupoid, userid, subject, content) values (UNHEX(?), unhex(?), unhex(?), ?, ?)";
    public final static String GET_TEMA_BY_ID = "select hex(t.id) as id,hex(t.grupoid) as grupoid, hex(t.userid) as userid, t.subject, t.content, t.creation_timestamp, t.last_modified, u.fullname from tema t, users u where t.id=unhex(?) and u.id=t.userid";
    public final static String GET_TEMAS = "select hex(id) as id, hex(grupoid) as grupoid, hex(userid) as userid, subject, creation_timestamp, last_modified from tema";
    public final static String UPDATE_TEMA = "update tema set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_TEMA = "delete from tema where id=unhex(?)";
}
