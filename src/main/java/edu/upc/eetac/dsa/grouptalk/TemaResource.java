package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.TemaDAO;
import edu.upc.eetac.dsa.grouptalk.dao.TemaDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.ColeccionTema;
import edu.upc.eetac.dsa.grouptalk.entity.RelacionGrupoUsuario;
import edu.upc.eetac.dsa.grouptalk.entity.Tema;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Marc on 26/10/2015.
 */
@Path("{id}")
public class TemaResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA)
    public Response createTema(@PathParam("id") String id, @FormParam("subject") String subject, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if (subject == null || content == null)
            throw new BadRequestException("all parameters are mandatory");
        String userid = securityContext.getUserPrincipal().getName();
        TemaDAO temaDAO = new TemaDAOImpl();
        Tema tema = null;
        AuthToken authenticationToken = null;
        try {
            RelacionGrupoUsuario relacionGrupoUsuario = temaDAO.getRelacion(id, userid);
            if (relacionGrupoUsuario == null)
                throw new ForbiddenException("You must suscribe to group to create temas");
            tema = temaDAO.createTema(id, userid, subject, content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + tema.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_TEMA).entity(tema).build();
    }
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA_COLLECTION)
    public ColeccionTema getTema(@PathParam("id") String id){
        ColeccionTema temaCollection = null;
        TemaDAO temaDAO = new TemaDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        try {
            RelacionGrupoUsuario relacionGrupoUsuario = temaDAO.getRelacion(id,userid);
            if (relacionGrupoUsuario == null)
                throw new ForbiddenException("You must suscribe to group to see temas");
            temaCollection = temaDAO.getTemas(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return temaCollection;
    }

    @Path("/tema/{idt}")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA)
    public Tema getTemas(@PathParam("id") String id, @PathParam("idt") String idt){
        Tema tema = null;
        TemaDAO temaDAO = new TemaDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        try {
            RelacionGrupoUsuario relacionGrupoUsuario = temaDAO.getRelacion(id,userid);
            if (relacionGrupoUsuario == null)
                throw new ForbiddenException("You must suscribe to group to see temas");
            tema = temaDAO.getTemaById(idt);
            if(tema == null)
                throw new NotFoundException("Tema with id = "+idt+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return tema;
    }
    @Path("/tema/{idt}")
    @PUT
    @Consumes(GroupTalkMediaType.GROUPTALK_TEMA)
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA)
    public Tema updateTema(@PathParam("idt") String idt, Tema tema) {
        if(tema == null)
            throw new BadRequestException("entity is null");
        if(!idt.equals(tema.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(tema.getUserid()))
            throw new ForbiddenException("operation not allowed");

        TemaDAO temaDAO = new TemaDAOImpl();
        try {
            tema = temaDAO.updateTema(idt, tema.getSubject(), tema.getContent());
            if(tema == null)
                throw new NotFoundException("Tema with id = "+idt+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return tema;
    }
    @Path("/tema/{idt}")
    @DELETE
    public void deleteTema(@PathParam("idt") String idt) {
        String userid = securityContext.getUserPrincipal().getName();
        TemaDAO temaDAO = new TemaDAOImpl();
        try {
            String ownerid = temaDAO.getTemaById(idt).getUserid();
            if(!userid.equals(ownerid)&&!securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");
            if(!temaDAO.deleteTema(idt))
                throw new NotFoundException("Tema with id = "+idt+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


}
