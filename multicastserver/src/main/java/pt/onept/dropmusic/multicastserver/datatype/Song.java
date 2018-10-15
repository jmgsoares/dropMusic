package pt.onept.dropmusic.multicastserver.datatype;

public class Song {
    private String name;
    public Song() {
    }
    public String getName() { return name; }

    public Song setName(String name) {
        this.name = name;
        return this;
    }
}