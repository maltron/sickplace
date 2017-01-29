package net.nortlam.errorcollection.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoContentMapper implements ExceptionMapper<NoContentException> {

    @Override
    public Response toResponse(NoContentException ex) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
