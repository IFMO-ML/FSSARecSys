package ru.ifmo.ctddev.FSSARecSys.calculators;

import ru.ifmo.ctddev.FSSARecSys.EvaluationResult;
import ru.ifmo.ctddev.FSSARecSys.db.DataSet;

public interface Evaluator {
    public EvaluationResult evaluate(DataSet dataSet);
}
