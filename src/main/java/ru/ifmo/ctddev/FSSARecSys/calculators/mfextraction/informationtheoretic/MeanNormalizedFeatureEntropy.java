package ru.ifmo.ctddev.FSSARecSys.calculators.mfextraction.informationtheoretic;

import weka.core.Instances;

import static ru.ifmo.ctddev.FSSARecSys.utils.InformationTheoreticUtils.*;

/**
 * Created by warrior on 23.03.15.
 */
public class MeanNormalizedFeatureEntropy extends AbstractDiscretizeExtractor {

    public static final String NAME = "Mean normalized feature entropy";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected double extractInternal(Instances instances) {
        double sum = 0;
        int count = 0;
        for (int i = 0; i < instances.numAttributes(); i++) {
            if (isNonClassNominalAttribute(instances, i)) {
                count++;
                double[] values = instances.attributeToDoubleArray(i);
                EntropyResult result = entropy(values, instances.attribute(i).numValues());
                sum += result.normalizedEntropy;
            }
        }
        return sum / count;
    }
}
