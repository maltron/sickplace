package net.nortlam.usermanagement.error;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Represents an existing duplication during the creation
 * 
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
@Provider
public class CreateMapper implements ExceptionMapper<CreateException> {

    private static final Logger LOG = Logger.getLogger(CreateMapper.class.getName());

    @Override
    public Response toResponse(CreateException exception) {
        return Response.status(Response.Status.CONFLICT).build();
    }
}
