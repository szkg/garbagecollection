package szkg.repositories;

import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;

/**
 * Created by Gencay on 21.06.2015.
 */
public interface IRepository {
    public void open() throws Exception;

    public void addExecution(int coreCount, String collector, int listSize, SorterType algorithm, ListType listType, double collectionTime, double executionTime, int collectedGarbage, int peakHeapSize) throws Exception;

    public void exportToScript() throws Exception;
}
