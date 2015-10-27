package edu.upc.eetac.dsa.grouptalk;

/**
 * Created by Marc on 26/10/2015.
 */

import edu.upc.eetac.dsa.grouptalk.dao.RespuestaDAO;
import edu.upc.eetac.dsa.grouptalk.dao.RespuestaDAOImpl;
import edu.upc.eetac.dsa.grouptalk.dao.TemaDAO;
import edu.upc.eetac.dsa.grouptalk.dao.TemaDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.ColeccionRespuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RelacionGrupoUsuario;
import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Path("tema={id}")
public class RespuestaResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_RESPUESTA)
    public Response createTema(@PathParam("id") String id, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if(content == null)
            throw new BadRequestException("all parameters are mandatory");
        String userid = securityContext.getUserPrincipal().getName();
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        Respuesta respuesta = null;
        AuthToken authenticationToken = null;
        try {
            RelacionGrupoUsuario relacionGrupoUsuario = respuestaDAO.getRelacion(id, userid);
            if (relacionGrupoUsuario == null)
                throw new ForbiddenException("You must suscribe to group to create respuestas");
            respuesta = respuestaDAO.createRespuesta(id, securityContext.getUserPrincipal().getName(), content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + respuesta.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_TEMA).entity(respuesta).build();
    }

    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_RESPUESTA_COLLECTION)
    public ColeccionRespuesta getRespuesta(@PathParam("id") String id,@PathParam("idt") String idt){
        ColeccionRespuesta respuestaCollection = null;
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();

        try {
            RelacionGrupoUsuario relacionGrupoUsuario = respuestaDAO.getRelacion(id,userid);
            if (relacionGrupoUsuario == null)
                throw new ForbiddenException("You must suscribe to group to see respuestas");
            respuestaCollection = respuestaDAO.getRespuestas(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return respuestaCollection;
    }

    @Path("/respuesta/{idt}")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA)
    public Respuesta getRespuestas(@PathParam("id") String id,@PathParam("idt") String idt){
        Respuesta respuesta = null;
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        try {
            RelacionGrupoUsuario relacionGrupoUsuario = respuestaDAO.getRelacion(id,userid);
            if (relacionGrupoUsuario == null)
                throw new ForbiddenException("You must suscribe to group to see respuestas");
            respuesta = respuestaDAO.getRespuestaById(idt);
            if(respuesta == null)
                throw new NotFoundException("Respuesta with id = "+idt+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return respuesta;
    }
    @Path("/respuesta/{idt}")
    @PUT
    @Consumes(GroupTalkMediaType.GROUPTALK_TEMA)
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA)
    public Respuesta updateTema(@PathParam("idt") String idt, Respuesta respuesta) {

        if(respuesta == null)
            throw new BadRequestException("entity is null");
        if(!idt.equals(respuesta.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(respuesta.getUserid()))
            throw new ForbiddenException("operation not allowed");

        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        try {
            respuesta = respuestaDAO.updateRespuesta(idt, respuesta.getContent());
            if(respuesta == null)
                throw new NotFoundException("Respuesta with id = "+idt+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return respuesta;
    }
    @Path("/respuesta/{idt}")
    @DELETE
    public void deleteRespuesta(@PathParam("id") String id, @PathParam("idt") String idt) {
        String userid = securityContext.getUserPrincipal().getName();
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        TemaDAO temaDAO = new TemaDAOImpl();
        try {
            String ownerid = respuestaDAO.getRespuestaById(idt).getUserid();
            String owneridt = temaDAO.getTemaById(id).getUserid();
            if(!userid.equals(ownerid)&&!securityContext.isUserInRole("admin")&&!userid.equals(owneridt))
                throw new ForbiddenException("operation not allowed");
            if(!respuestaDAO.deleteRespuesta(idt))
                throw new NotFoundException("Respuesta with id = "+idt+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


}
