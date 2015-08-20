package ru.ifmo.ctddev.FSSARecSys.alternative.internal;

import ru.ifmo.ctddev.FSSARecSys.utils.ClusterCentroid;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.clusterers.Clusterer;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Администратор on 19.08.2015.
 */
public class Clusterisation {

    private ArrayList<Instances> clusters;
    private Instances unitedClusters;
    private Instances centroids;
    private Clusterer clusterer;

    private Integer numOfClusters;

    //clusterer should be already be built on the whole dataset
    public Clusterisation(ArrayList<Instances> clusters, Clusterer clusterer) {
        this.clusters = clusters;
        this.clusterer = Objects.requireNonNull(clusterer);
        numOfClusters = clusters.size();

        unitedClusters = getAllInstances();
    }

    public Integer getNumClusters(){
        return numOfClusters;
    }

    //** dunno if needed **

    public Double averageInterClusterDistance(int clusterNum) {
        Instances cluster = clusters.get(clusterNum);
        EuclideanDistance distance = new EuclideanDistance(cluster);
        Double avg = 0.0;
        for (int i = 0; i < cluster.numInstances(); i++)
            for (int j = 0; j < cluster.numInstances(); j++){
                if (i != j) {
                    avg += distance.distance(cluster.instance(i), cluster.instance(j));
                }
            }
        avg /= (Math.pow(cluster.numInstances(), 2.0) - cluster.numInstances());
        return avg;
    }

    public Double averageIntraClusterDistance() {
        return null;
    }
    //**

    public Instance getClusterCentroid(int clusterNum) throws Exception {
        if (clusterer instanceof SimpleKMeans) {
            return ((SimpleKMeans) clusterer).getClusterCentroids().instance(clusterNum);
        } else {
            ClusterCentroid ct = new ClusterCentroid();
            return ct.findCentroid(clusterNum, clusters.get(clusterNum));
        }
    }

    private Instances getAllInstances(){
        Instances all = new Instances(clusters.get(0));
        for (Instances cluster: clusters) {
            for (int i = 1; i < cluster.numInstances(); i++){
                all.add(cluster.instance(i));
            }
        }
        return all;
    }

    public Double DunnIndex(){

        //get max inter-cluster distance

        EuclideanDistance allInstancedDist = new EuclideanDistance(unitedClusters);

        Double maxTotal = Double.MIN_VALUE;
        Double maxForCluster = Double.MIN_VALUE;
        for (int i = 0; i < numOfClusters; i++) {
            Instances currCluster = clusters.get(i);
            maxTotal = Double.MIN_VALUE;
            maxForCluster = Double.MIN_VALUE;
            for (int j = 0; j < currCluster.numInstances(); j++) {
                Instance currInstance = currCluster.instance(j);
                maxForCluster = Double.MIN_VALUE;
                for (int k = 0; k < currCluster.numInstances(); k++) {
                    if (j != k)
                        maxForCluster = Double.max(maxForCluster,
                                allInstancedDist.distance(currInstance, currCluster.instance(k)));
                }
            }
            maxTotal = Double.max(maxTotal, maxForCluster);
        }

        //get min intra-cluster distance

        boolean [][] intraClusterMatrix = new boolean[numOfClusters][numOfClusters];
        
        for (int i = 0; i < numOfClusters; i++) {
            for (int j = 0; j < numOfClusters; j++) {

            }
        }
        return 0.0;
    }

    public Double DaviesBouldinIndex() throws Exception {
        //count S_i = 1 / |C_i| sum_all_xi(dist(x_i, centr_i))

        ArrayList<Double> sTemp = new ArrayList<>(numOfClusters);
        ArrayList<EuclideanDistance> euclideanDistances = new ArrayList<>(clusters.size());

        for (int i = 0; i < numOfClusters; i++) {
            Double sumTmp = 0.0;
            Instances currentCluster = clusters.get(i);
            euclideanDistances.set(i, new EuclideanDistance(currentCluster));

            Instance centroid = getClusterCentroid(i);
            centroids.add(centroid);

            for (int j = 0; j < currentCluster.numInstances(); j++){
                Instance instance = currentCluster.instance(j);
                sumTmp += euclideanDistances.get(i).distance(instance, centroid);
            }
            sTemp.set(i, sumTmp / currentCluster.numInstances());
        }

        //count D_i = max_j (i!=j) {(S_i + S_j) / dist(centr_i, centr_j)}

        ArrayList<Double> dTemp = new ArrayList<>(numOfClusters);

        EuclideanDistance centroidDist = new EuclideanDistance(centroids);

        for (int i = 0; i < clusters.size(); i++) {
            Double maxVal = Double.MIN_VALUE;
            for (int j = 0; j < clusters.size(); j++)
                if (i != j) {
                    maxVal = Double.max(maxVal, (sTemp.get(i) + sTemp.get(j)) /
                            centroidDist.distance(centroids.instance(i), centroids.instance(j)));

                }
            dTemp.set(i, maxVal);
        }
        Double result = 0.0;

        for (Double i: dTemp) {
            result += i;
        }

        return result / numOfClusters;
    }

    public Double silhouetteIndex(){

        return 0.0;
    }

}
