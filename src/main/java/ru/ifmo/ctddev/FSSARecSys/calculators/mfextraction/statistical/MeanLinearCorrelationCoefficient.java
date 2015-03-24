package ru.ifmo.ctddev.FSSARecSys.calculators.mfextraction.statistical;

import ru.ifmo.ctddev.FSSARecSys.calculators.mfextraction.MetaFeatureExtractor;
import ru.ifmo.ctddev.FSSARecSys.db.DataSet;
import ru.ifmo.ctddev.FSSARecSys.utils.StatisticalUtils;
import weka.core.Instances;

/**
 * Created by warrior on 22.03.15.
 */
public class MeanLinearCorrelationCoefficient extends AbstractStatisticalExtractor {

    private static final String NAME = "Mean absolute linear correlation coefficient of all possible pairs of features";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double extract(DataSet dataSet) {
        Instances instances = dataSet.getInstances();
        double sum = 0;
        int count = 0;
        for (int i = 0; i < instances.numAttributes(); i++) {
            if (isNonClassNumericalAttribute(instances, i)) {
                double[] values1 = instances.attributeToDoubleArray(i);
                for (int j = i + 1; j < instances.numAttributes(); i++) {
                    if (isNonClassNumericalAttribute(instances, j)) {
                        double[] values2 = instances.attributeToDoubleArray(i);
                        sum += StatisticalUtils.linearCorrelationCoefficient(values1, values2);
                        count++;
                    }
                }
            }
        }
        return sum / count;
    }
}
