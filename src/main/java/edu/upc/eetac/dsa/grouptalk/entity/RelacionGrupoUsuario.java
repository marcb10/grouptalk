package edu.upc.eetac.dsa.grouptalk.entity;

/**
 * Created by Marc on 27/10/2015.
 */
public class RelacionGrupoUsuario {
    private String id;
    private String grupoid;
    private String userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(String grupoid) {
        this.grupoid = grupoid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
