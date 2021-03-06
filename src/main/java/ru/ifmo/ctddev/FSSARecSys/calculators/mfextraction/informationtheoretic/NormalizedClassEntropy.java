package ru.ifmo.ctddev.FSSARecSys.calculators.mfextraction.informationtheoretic;

import weka.core.Instances;

import static ru.ifmo.ctddev.FSSARecSys.utils.InformationTheoreticUtils.*;

/**
 * Created by warrior on 23.03.15.
 */
public class NormalizedClassEntropy extends AbstractDiscretizeExtractor {

    public static final String NAME = "Normalized class entropy";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected double extractInternal(Instances instances) {
        int classIndex = instances.classIndex();
        if (classIndex < 0) {
            throw new IllegalArgumentException("dataset hasn't class attribute");
        }
        double[] values = instances.attributeToDoubleArray(classIndex);
        EntropyResult result = entropy(values, instances.classAttribute().numValues());
        return result.normalizedEntropy;
    }
}
