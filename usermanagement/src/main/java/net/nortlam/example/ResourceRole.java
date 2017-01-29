package net.nortlam.example;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.nortlam.example.entity.Role;
import net.nortlam.example.error.CreateException;
import net.nortlam.example.error.MissingInformationException;
import net.nortlam.example.error.NoContentException;
import org.bson.Document;
import org.bson.json.JsonParseException;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
@Path("/v1/role")
public class ResourceRole implements Serializable {

    private static final Logger LOG = Logger.getLogger(ResourceRole.class.getName());
    
    public ResourceRole() {
        LOG.log(Level.INFO, ">>> ResourceRole()");
    }
    
    @EJB
    private Service service;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAll() throws NoContentException {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for(Role role: service.fetchAllRoles())
            builder.add(role.toDocument().toJson());
        
        return Response.ok(builder.build()).build();
    }
    
    /**
     * Success:
     * Status.CREATED (201): The content was successfully created
     * 
     * Failure:
     * CreateException:             Status.CONFLICT (409): Unable to Insert data into Database
     * MissingInformationException: Status.NOT_ACCEPTABLE (406): 
     *                Tag <name> is missing or there isn't any content at all
     * JsonParseException:          Status.BAD_REQUEST (400): JSON Content was unable to parse
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String content) throws CreateException, MissingInformationException {
        LOG.log(Level.INFO, ">>> create() \"{0}\"", content != null ? content : "NULL");
        if(content == null || (content != null && content.isEmpty())) 
                                throw new MissingInformationException("No Content");
        try {
            // Is this a valid document ?
            Document document = Document.parse(content);
            
            // Get into Role and Check if it's valid 
            Role role = new Role(document);
            
            // So far, so good. Insert the Document
            service.create(role);
            
        } catch(JsonParseException ex) {
            LOG.log(Level.WARNING, "### JSON PARSE EXCEPTION:{0}", ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        return Response.status(Response.Status.CREATED).build();
    }
}
