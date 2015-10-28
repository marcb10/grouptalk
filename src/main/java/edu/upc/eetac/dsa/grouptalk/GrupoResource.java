package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.GrupoDAO;
import edu.upc.eetac.dsa.grouptalk.dao.GrupoDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.ColeccionGrupo;
import edu.upc.eetac.dsa.grouptalk.entity.Grupo;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Marc on 25/10/2015.
 */
@Path("grupo")
public class GrupoResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_GRUPO)
    public Response createGrupo(@FormParam("nombre") String nombre, @Context UriInfo uriInfo) throws URISyntaxException {
        if (!securityContext.isUserInRole("admin"))
            throw new ForbiddenException("You are not allowed to create a group.");
        if(nombre== null)
            throw new BadRequestException("all parameters are mandatory");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        AuthToken authenticationToken = null;
        try{
            grupo = grupoDAO.createGrupo(nombre, securityContext.getUserPrincipal().getName());
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + grupo.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_GRUPO).entity(grupo).build();
    }
    @Path("/{id}")
    @POST
    @Produces(GroupTalkMediaType.GROUPTALK_GRUPO)
    public void suscribirGrupo(@PathParam("id") String id)  {

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        AuthToken authenticationToken = null;
        try{
            grupoDAO.suscribirGrupo(id, securityContext.getUserPrincipal().getName());
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_GRUPO_COLLECTION)
    public ColeccionGrupo getGrupo(){
        ColeccionGrupo grupoCollection = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupoCollection = grupoDAO.getGrupos();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return grupoCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_GRUPO)
    public Grupo getGrupo(@PathParam("id") String id){
        Grupo grupo = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupo = grupoDAO.getGrupoById(id);
            if(grupo == null)
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return grupo;
    }
    @Path("/{id}")
    @DELETE
    public void deleteGrupo(@PathParam("id") String id) {
        if (!securityContext.isUserInRole("admin"))
          throw new ForbiddenException("You are not allowed to delete a group.");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {

            if(!grupoDAO.deleteGrupo(id))
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
    @Path("/su={id}")
    @DELETE
    public void desuscribirGrupo(@PathParam("id") String id) throws URISyntaxException {

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        try{
            if(!grupoDAO.dessuscribirGrupo(id, userid))
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


}
