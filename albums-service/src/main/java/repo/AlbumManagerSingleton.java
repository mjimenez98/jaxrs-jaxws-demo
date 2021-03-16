package repo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum AlbumManagerSingleton {

    INSTANCE;

    repo.AlbumManagerImpl albumManagerImplementation;

    public void setAlbumManagerImplementation(String className){
        try {
            Class<?> albumManagerImplementationClass = Class.forName(className);
            // Call parameterized constructor
            Class<?>[] type = {};
            //Get parameterized constructor
            Constructor<?> albumManagerConstructor = albumManagerImplementationClass.getConstructor(type);
            //Instantiate object
            albumManagerImplementation = (repo.AlbumManagerImpl) albumManagerConstructor.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("Could not set Album Manager Implementation");
            e.printStackTrace();
        }
    }

    public repo.AlbumManagerImpl getAlbumManagerImplementation(){
        return albumManagerImplementation;
    }
}
