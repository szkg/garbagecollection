package szkg.processManager;

import java.io.IOException;

import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;


public class ProcessCreator {

	public static void main(String[] args)  throws IOException, InterruptedException {

		String jarName = args[0];
		int executionIterationCount = 1;

		int sortCountStart = 10;
		int sortCountEnd = 100;
		int sortCountIncrement = 10;

		int listSizeStart = 10000;
		int listSizeEnd = 100000;
		int listSizeIncrement = 10000;

		for(int executionIteration = 0; executionIteration < executionIterationCount; executionIteration++){
			for(int sortCount = sortCountStart; sortCount < sortCountEnd; sortCount = sortCount + sortCountIncrement){
				for(int listSize = listSizeStart; listSize < listSizeEnd; listSize = listSize + listSizeIncrement){
					for(ListType listType : ListType.values()){
						for(SorterType algorithm : SorterType.values()){

							Thread.sleep(10000);

							Process process = Runtime.getRuntime().exec(String.format(
									"java -Xloggc:Logs/%d_%d_%d_%d_%d.txt -jar %s %d %d %d %d",

									algorithm.getValue(),
									listType.getValue(),
									listSize,
									sortCount,									
									executionIteration,	

									jarName,

									algorithm.getValue(),
									listType.getValue(),
									listSize,
									sortCount
									));

							process.waitFor();	
						}
					}
				}
			}
		}
	}

}
