import org.junit.Test;
import pt.onept.dropmusic.multicastserver.datatype.User;

public class UserTest {
    User t0 = new User()
            .setName("soares")
            .setEmail("teste Email soares")
            .setLogin("soares")
            .setPassword("soares")
            .setEditor(false);
    User t1 = new User()
            .setName("sabao")
            .setEmail("teste Email sabao")
            .setLogin("sabao")
            .setPassword("sabao")
            .setEditor(true);
    User t2 = new User()
            .setName("pasquim")
            .setEmail("teste Email pasquim")
            .setLogin("pasquim")
            .setPassword("pasquim")
            .setEditor(false);
    User t3 = new User()
            .setName("pasquim")
            .setEmail("teste Email pasquim")
            .setLogin("pasquim")
            .setPassword("pasquim")
            .setEditor(false);

    @Test
    public void createuser(){

    assert(t0.getName().equals("soares"));
    }

    @Test
    public void getName() {
    }

    @Test
    public void setName() {
    }

    @Test
    public void getEmail() {
    }

    @Test
    public void setEmail() {
    }

    @Test
    public void getLogin() {
    }

    @Test
    public void setLogin() {
    }

    @Test
    public void checkPassword() {
    }

    @Test
    public void setPassword() {
    }

    @Test
    public void isEditor() {
    }

    @Test
    public void setEditor() {
    }

    @Test
    public void createPlaylist() {
    }

    @Test
    public void getPlaylists() {
    }
}
