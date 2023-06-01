package it.intesys.academy.repository;

public class DummyConfigRepository implements ConfigRepository{
    @Override
    public String getProperty(String key) {
        return key + " value";
    }
}
