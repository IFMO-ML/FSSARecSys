package ru.ifmo.ctddev.FSSARecSys.db;

import ru.ifmo.ctddev.FSSARecSys.calculators.ClassifierEvaluator;

import java.io.File;
import java.util.List;

public interface ClassifierManager {
    public List<String> getAvailableClassifierNames();

    public ClassifierEvaluator get(String name);

    public void register(String name, String className);
    public void register(String name, String className, String[] arguments);
    public void register(String name, String className, File jarFile);
    public void register(String name, String className, String[] arguments, File jarFile);
}
