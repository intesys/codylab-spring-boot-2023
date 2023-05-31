package it.intesys.issuetracker.service;

import it.intesys.academy.repository.ConfigRepository;

public class DummyConfigRepository implements ConfigRepository {
    @Override
    public String getProperty(String key) {
        return key + " value";
    }

}
