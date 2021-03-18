package service;

import core.Cover;
import exceptions.RepException;
import org.glassfish.jersey.media.multipart.*;
import repo.AlbumManagerImpl;
import core.Album;
import repo.AlbumManagerSingleton;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.util.Comparator;

/**
 * Root resource (exposed at "albums" path)
 */
@Path("albums")
public class Albums {
    private static AlbumManagerImpl manager;
    private static boolean isManagerCreated = false;

    private void initialize() throws Exception {
        if (isManagerCreated)
            throw new Exception();

        isManagerCreated = true;
        AlbumManagerSingleton managerSingleton = AlbumManagerSingleton.INSTANCE;
        managerSingleton.setAlbumManagerImplementation("repo.AlbumManagerImpl");
        manager = managerSingleton.getAlbumManagerImplementation();
    }

//    ALBUM

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addAlbum(@FormDataParam("album") FormDataBodyPart albumJson,
                             @FormDataParam("file") FormDataBodyPart coverFile) {
        try {
            if (!isManagerCreated)
                initialize();

            // Parse album
            albumJson.setMediaType(MediaType.APPLICATION_JSON_TYPE);
            Album album = albumJson.getValueAs(Album.class);

            if (album.getIsrc() == null || album.getTitle() == null || album.getReleaseYear() == 0 ||
                    album.getArtist() == null) {
                throw new RepException("Request could not be processed: Parameter missing");
            }

            Album existingAlbum = manager.getAlbum(album.getIsrc());
            if (existingAlbum == null) {
                // Create album
                Album newAlbum = new Album(album);
                manager.createAlbum(newAlbum);

                // Add album cover
                InputStream is = coverFile.getEntityAs(InputStream.class);
                MediaType md = coverFile.getMediaType();
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
                    album.getArtist() == null) {
                throw new RepException("Request could not be processed: Parameter missing");
            }

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

//    ALBUM COVER

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
                MediaType md = coverFile.getMediaType();

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

            if (cover == null)
                return Response.status(Response.Status.OK)
                        .entity("Album cover not found!")
                        .type(MediaType.APPLICATION_JSON)
                        .build();

            StreamingOutput fileStream = output -> {
                try {
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
                catch (Exception e) {
                    throw new RepException("File Not Found !!");
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
