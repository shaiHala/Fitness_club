package univ.master.mql.administrationservice.config;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;


public class FooEventListenerProvider  implements EventListenerProvider {
    private final KeycloakSession session;

    public FooEventListenerProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() != EventType.REGISTER) return;

        // load the realm by event.getRealmId()
        RealmModel realm = session.realms().getRealm(event.getRealmId());

        // load the user by event.getUserId()
        UserModel user = session.users().getUserById(event.getUserId(), realm);

        // your logic using user.getFirstName() and user.getLastName()

        System.err.println(user);

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}
