package szkg.repositories;

import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;

/**
 * Created by Gencay on 21.06.2015.
 */
public interface IRepository {
    public void open() throws Exception;

    public void addSortingExecution(int coreCount, String collector, int listSize, SorterType algorithm, ListType listType, double collectionTime, double executionTime, int collectedGarbage, int peakHeapSize, String jdkVersion) throws Exception;

    public void addDeCapoExecution(int coreCount, String collector, String benchmark, double collectionTime, double executionTime, int collectedGarbage, int peakHeapSize, String jdkVersion) throws Exception;

}
