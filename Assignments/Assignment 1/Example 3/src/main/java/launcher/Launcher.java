package launcher;

import controller.LoginController;
import database.Boostraper;
import launcher.ComponentFactory;

import java.sql.SQLException;

public class Launcher {

    public static boolean BOOTSTRAP = true;

    public static void main(String[] args) {
        bootstrap();
        ComponentFactory componentFactory = ComponentFactory.instance(false);

        new LoginController(componentFactory.getLoginView(),
                componentFactory.getAuthenticationService(),
                componentFactory.getAdminController(),
                componentFactory.getEmployeeController(),
                componentFactory.getUserRepository());
    }
    private static void bootstrap() {
        if (BOOTSTRAP) {
            try {
                new Boostraper().execute();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }
}