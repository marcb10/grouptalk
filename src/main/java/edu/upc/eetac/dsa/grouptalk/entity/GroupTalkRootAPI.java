package edu.upc.eetac.dsa.grouptalk.entity;

import edu.upc.eetac.dsa.grouptalk.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Marc on 27/10/2015.
 */
public class GroupTalkRootAPI {
    @InjectLinks({
            @InjectLink(resource = GroupTalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self bookmark home", title = "Group Talk Root API"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "login", title = "Login",  type= GroupTalkMediaType.GROUPTALK_AUTH_TOKEN),
            @InjectLink(resource = GrupoResource.class, style = InjectLink.Style.ABSOLUTE, rel = "grupos", title = "Grupos", type= GroupTalkMediaType.GROUPTALK_GRUPO_COLLECTION),
            @InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-user", title = "Register", type= GroupTalkMediaType.GROUPTALK_AUTH_TOKEN),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout", condition="${!empty resource.userid}"),
            @InjectLink(resource = GrupoResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-grupo", title = "Create grupo", condition="${!empty resource.userid}", type=GroupTalkMediaType.GROUPTALK_GRUPO),
            @InjectLink(resource = UserResource.class, method="getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", condition="${!empty resource.userid}", type= GroupTalkMediaType.GROUPTALK_USER, bindings = @Binding(name = "id", value = "${resource.userid}"))
    })
    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
