//Sources: https://stackoverflow.com/questions/44520887/how-to-download-a-csv-file-by-streamingoutput
package service;

import core.Cover;
import exceptions.RepException;
import org.glassfish.jersey.media.multipart.*;
import repo.AlbumManagerImpl;
import core.Album;
import repo.AlbumManagerSingleton;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;

/**
 * Root resource (exposed at "albums" path)
 */
@Path("albums")
public class Albums {
    private static AlbumManagerImpl manager;
    private static boolean isManagerCreated = false;
    //private static String BASE = "/Users/dina/Documents/GitHub/jaxrs-jaxws-demo/albums-service/src/covers";
    //private static final String COVERS_PATH = "/covers";

    private void initialize() throws Exception {
        if (isManagerCreated)
            throw new Exception();

        isManagerCreated = true;
        AlbumManagerSingleton managerSingleton = AlbumManagerSingleton.INSTANCE;
        managerSingleton.setAlbumManagerImplementation("repo.AlbumManagerImpl");
        manager = managerSingleton.getAlbumManagerImplementation();
    }

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addAlbum(@FormDataParam("album") FormDataBodyPart albumJson,
                             @FormDataParam("file") FormDataBodyPart coverFile) {
        try {
            if (!isManagerCreated)
                initialize();

            albumJson.setMediaType(MediaType.APPLICATION_JSON_TYPE);
            Album album = albumJson.getValueAs(Album.class);

            if (album.getIsrc() == null || album.getTitle() == null || album.getReleaseYear() == 0 ||
                    album.getArtist() == null) {
                throw new RepException("Request could not be processed: Parameter missing");
            }

            Album existingAlbum = manager.getAlbum(album.getIsrc());
            if (existingAlbum == null) {
                Album newAlbum = new Album(album);
                manager.createAlbum(newAlbum);

                InputStream is = coverFile.getEntityAs(InputStream.class);

                FormDataContentDisposition fd = coverFile.getFormDataContentDisposition();
                //String fileName = fd.getFileName();

                MediaType md = coverFile.getMediaType();
                //String fileLocation = BASE + "/" + fileName;
                manager.updateAlbumCoverImage(is, album.getIsrc(), md);
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
            if (foundAlbum == null) {
                throw new RepException("Album not found!");
            }
            else {
                return Response.status(Response.Status.OK).entity(foundAlbum).type(MediaType.APPLICATION_JSON).build();
            }
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

            Album[] albums = manager.getAlbums().stream().sorted(Comparator.comparing(Album::getTitle)).toArray(Album[]::new);
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
    public Response updateAlbumCoverImage(@FormDataParam("file") FormDataBodyPart coverFile,
                                          @PathParam("isrc") String isrc) {
        try {
            if (!isManagerCreated)
                initialize();
            Album foundAlbum = manager.getAlbum(isrc);
            if (foundAlbum == null) {
                throw new RepException("Album not found!");
            }
            else {
                InputStream is = coverFile.getEntityAs(InputStream.class);
                FormDataContentDisposition fd = coverFile.getFormDataContentDisposition();
                //String fileName = fd.getFileName();
                MediaType md = coverFile.getMediaType();
                //String fileLocation = BASE + "/" + fileName;
                manager.updateAlbumCoverImage(is, isrc, md);
            }
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
    @Produces("image/jpeg")
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
                        //java.nio.file.Path path = Paths.get(cover.getImage());
                        //byte[] data = Files.readAllBytes(path);
                        //output.write(data);
                        //output.flush();
                        InputStream is = cover.getBlob();
                        byte[] bytes = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(bytes)) != -1) {
                            output.write(bytes, 0, bytesRead);
                        }
                        is.close();
                        output.flush();
                        output.close();
                    }
                    catch (Exception e)
                    {
                        throw new RepException("File Not Found !!");
                    }
                }
            };
            return Response
                    .ok(fileStream, cover.getMimeType())
                    .header("content-disposition","inline")
                    .build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
