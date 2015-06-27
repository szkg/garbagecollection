package szkg.repositories;

import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;

/**
 * Created by Gencay on 21.06.2015.
 */
public interface IRepository {
    public void open() throws Exception;

    public void addExecution(String collector, int listSize, SorterType algorithm, ListType listType, double collectionTime, double executionTime) throws Exception;

}
