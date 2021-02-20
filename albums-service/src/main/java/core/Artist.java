package core;

public class Artist {
    private String firstName;
    private String lastName;

    public Artist() {
        firstName = null;
        lastName = null;
    }

    public Artist(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Artist(Artist artist) {
        this.firstName = artist.firstName;
        this.lastName = artist.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
