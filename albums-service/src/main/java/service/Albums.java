//Sources: https://stackoverflow.com/questions/44520887/how-to-download-a-csv-file-by-streamingoutput
package service;

import core.Cover;
import exceptions.RepException;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import repo.AlbumManagerImpl;
import core.Album;
import repo.AlbumManagerSingleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Root resource (exposed at "albums" path)
 */
@Path("albums")
public class Albums {
    private static AlbumManagerImpl manager;
    private static boolean isManagerCreated = false;
    private static final String COVERS_PATH = "/covers";

    private void initialize() throws Exception {
        if (isManagerCreated)
            throw new Exception();
        isManagerCreated = true;
        AlbumManagerSingleton managerSingleton = AlbumManagerSingleton.INSTANCE;
        managerSingleton.setAlbumManagerImplementation("business.AlbumManagerImpl");
        manager = managerSingleton.getAlbumManagerImplementation();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addAlbum(Album album) {
        try {
            if (!isManagerCreated)
                initialize();
            if (album.getIsrc() == null || album.getTitle() == null || album.getReleaseYear() == 0 ||
                    album.getArtist() == null)
                throw new RepException("Request could not be processed: Parameter missing");

            Album existingAlbum = manager.getAlbum(album.getIsrc());

            if (existingAlbum == null) {
                Album newAlbum = new Album(album);
                manager.createAlbum(newAlbum);
            } else {
                throw new RepException("Album already exists!");
            }
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateAlbum(Album album) {
        try {
            if (!isManagerCreated)
                initialize();
            if (album.getIsrc() == null || album.getTitle() == null || album.getReleaseYear() == 0 ||
                    album.getArtist() == null)
                throw new RepException("Request could not be processed: Parameter missing");
            Album existingAlbum = manager.getAlbum(album.getIsrc());
            if (existingAlbum == null) {
                throw new RepException("Album not found!");
            } else {
                Album newAlbum = new Album(album);
                manager.updateAlbum(newAlbum);
            }
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("{isrc}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteAlbum(@PathParam("isrc") String isrc) {
        try {
            if (!isManagerCreated)
                initialize();

            Album existingAlbum = manager.getAlbum(isrc);
            if (existingAlbum == null) {
                throw new RepException("Album not found!");
            } else {
                manager.deleteAlbum(isrc);
            }
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("{isrc}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAlbum(@PathParam("isrc") String isrc) {
        try {
            if (!isManagerCreated)
                initialize();
            Album foundAlbum = manager.getAlbum(isrc);
            return Response.status(Response.Status.OK).entity(foundAlbum).type(MediaType.APPLICATION_JSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }

    /**
     * List all albums alphabetically by title
     * @return Collection of ISRCs and titles
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response listAlbums() {
        try {
            if (!isManagerCreated)
                initialize();
           ArrayList<Album> albums = manager.getAlbums().stream().sorted(Comparator.comparing(Album::getTitle)).collect(Collectors.toCollection(ArrayList::new));
           return Response.status(Response.Status.OK).entity(albums).type(MediaType.APPLICATION_JSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("{isrc}")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateAlbumCoverImage(FormDataMultiPart input, @PathParam("isrc") String isrc) {
        try {
            FormDataBodyPart bodyPart = input.getField("file");
            FormDataContentDisposition fdcd = bodyPart.getFormDataContentDisposition();
            String filename = fdcd.getFileName();
            MediaType mimeType = bodyPart.getMediaType();
            System.out.println("mimeType: " + mimeType);
            InputStream body = bodyPart.getValueAs(InputStream.class);
            String fileLocation = COVERS_PATH + "/" + isrc + "/" + filename;
            manager.updateAlbumCoverImage(body, fileLocation, mimeType, isrc);
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/cover/{isrc}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteAlbumCoverImage(@PathParam("isrc") String isrc) {
        try {
            if (!isManagerCreated)
                initialize();
            Album existingAlbum = manager.getAlbum(isrc);
            if (existingAlbum == null) {
                throw new RepException("Album not found!");
            } else {
                manager.deleteAlbumCoverImage(isrc);
            }
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/cover/{isrc}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAlbumCoverImage(@PathParam("isrc") String isrc) {
        try {
            if (!isManagerCreated)
                initialize();
            Cover cover = manager.getAlbumCoverImage(isrc);
            StreamingOutput fileStream = new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException, WebApplicationException {
                    try
                    {
                        java.nio.file.Path path = Paths.get(cover.getImage());
                        byte[] data = Files.readAllBytes(path);
                        output.write(data);
                        output.flush();
                    }
                    catch (Exception e)
                    {
                        throw new RepException("File Not Found !!");
                    }

                }
            };
            return Response
                    .ok(fileStream, cover.getMimeType())
                    .header("content-disposition","attachment; filename = " + cover.getImage().split(isrc+"/")[1])
                    .build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
