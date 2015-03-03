package ru.ifmo.ctddev.FSSARecSys.calculators;

import ru.ifmo.ctddev.FSSARecSys.ClassifierResult;
import ru.ifmo.ctddev.FSSARecSys.db.DataSet;

public interface Classifier {
    public ClassifierResult evaluate(DataSet dataSet);
}
