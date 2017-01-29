package net.nortlam.usermanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import net.nortlam.usermanagement.entity.Country;
import net.nortlam.usermanagement.error.NoContentException;

@Path("/v1/user")
public class Resource {

    private static final Logger LOG = Logger.getLogger(Resource.class.getName());
    
    @Context
    private UriInfo info;
    
    @EJB
    private Service service;
    
    /**
     * Used as a means to select which fields needs to be used */
    private Collection<String> queryParameters() {
        Collection<String> queryParameters = new ArrayList<>();
        for(String queryParam: info.getQueryParameters().keySet())
            queryParameters.add(queryParam);
        
        return queryParameters;
    }
}
