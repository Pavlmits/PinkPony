package model;

import java.util.List;

/**
 *
 * @param <V> cluster of what
 */
public class Cluster<V> {

    private final List<V> clusterList;

    private int density;

    private int weight;


    public Cluster(final List<V> clusterList) {
        this.clusterList = clusterList;
    }

    public void add(V object){
        clusterList.add(object);
    }


}
