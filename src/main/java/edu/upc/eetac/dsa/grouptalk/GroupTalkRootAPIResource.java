package edu.upc.eetac.dsa.grouptalk;

/**
 * Created by Marc on 27/10/2015.
 */

import edu.upc.eetac.dsa.grouptalk.entity.GroupTalkRootAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/")
public class GroupTalkRootAPIResource {
    @Context
    private SecurityContext securityContext;

    private String userid;


    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_ROOT)
    public GroupTalkRootAPI getRootAPI() {
        if(securityContext.getUserPrincipal()!=null)
            userid = securityContext.getUserPrincipal().getName();
        GroupTalkRootAPI groupTalkRootAPI = new GroupTalkRootAPI();

        return groupTalkRootAPI;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}