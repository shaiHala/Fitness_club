package univ.master.mql.administrationservice.config;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class FooEventListenerProviderFactory implements EventListenerProviderFactory{


    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new FooEventListenerProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public int order() {
        return EventListenerProviderFactory.super.order();
    }
}
