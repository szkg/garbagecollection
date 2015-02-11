package szkg.processManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import szkg.algorithms.sort.ListType;
import szkg.algorithms.sort.SorterType;
import szkg.common.CollectionResult;


public class ProcessCreator {

	public static void main(String[] args) throws Exception {

		String jarName = args[0];

		runApplication(			
				jarName,
				"ConstantListSize.txt",
				10,
				
				100,
				200,
				10,
				
				50000,
				50000,
				1
				);


		runApplication(			
				jarName,
				"ConstantSortCount.txt",
				10,
				
				150,
				150,
				10,
				
				10000,
				100000,
				10000
				);
	}

	public static void runApplication(			
			String jarName,
			String resultFile,
			int executionIterationCount,
			int sortCountStart,
			int sortCountEnd,
			int sortCountIncrement,
			int listSizeStart,
			int listSizeEnd,
			int listSizeIncrement
			) throws Exception 
	{


		for(int sortCount = sortCountStart; sortCount <= sortCountEnd; sortCount = sortCount + sortCountIncrement){
			for(int listSize = listSizeStart; listSize <= listSizeEnd; listSize = listSize + listSizeIncrement){					

				ArrayList<CollectionResult> collectionTimes = new ArrayList<CollectionResult>(4);

				for(ListType listType : ListType.values()){						
					for(SorterType algorithm : new SorterType[]{ SorterType.Merge, SorterType.Quick }){

						Double minimumCollectionTime = Double.MAX_VALUE;
						Double maximumCollectionTime = Double.MIN_VALUE;
						Double totalCollectionTime = new Double(0);

						for(int executionIteration = 0; executionIteration < executionIterationCount; executionIteration++){


							String garbageLogFile = String.format("Logs%s%d_%d_%d_%d_%d.txt",
									File.separator,
									algorithm.getValue(),
									listType.getValue(),
									listSize,
									sortCount,									
									executionIteration);

							runProcess(
									jarName,
									garbageLogFile,
									algorithm,
									listType,
									listSize,
									sortCount,									
									executionIteration									
									);


							Double collectionTimeofAnIteration = getCollectionTimeofAnIteration(garbageLogFile);

							if(collectionTimeofAnIteration > maximumCollectionTime)
								maximumCollectionTime = collectionTimeofAnIteration;

							if(collectionTimeofAnIteration < minimumCollectionTime)
								minimumCollectionTime = collectionTimeofAnIteration;

							totalCollectionTime += collectionTimeofAnIteration;
						}

						CollectionResult collectionResult = new CollectionResult();

						collectionResult.Average = totalCollectionTime / executionIterationCount;
						collectionResult.Max = maximumCollectionTime;
						collectionResult.Min = minimumCollectionTime;

						collectionTimes.add(collectionResult);

					}
				}

				logResult(		
						resultFile,
						listSize,
						sortCount,	
						collectionTimes
						);

			}
		}
	}

	public static void runProcess(
			String jarName,
			String garbageLogFile,
			SorterType algorithm,
			ListType listType,
			int listSize,
			int sortCount,									
			int executionIteration
			) throws Exception {
		Thread.sleep(1000);

		System.out.println(
				String.format(
						"Process started. Parameters: iteration: %d, sortCount: %d, listSize: %d, listType: %s, algorithm: %s", 
						executionIteration,
						sortCount,
						listSize,
						listType.name(),
						algorithm.name()));

		Date startTime = new Date();							

		Process process = Runtime.getRuntime().exec(String.format(
				"java -Xloggc:%s -jar %s %d %d %d %d",
				garbageLogFile,
				jarName,
				algorithm.getValue(),
				listType.getValue(),
				listSize,
				sortCount
				));

		process.waitFor();	

		Date endTime = new Date();

		long elapsedTime= endTime.getTime() - startTime.getTime();

		System.out.println(
				String.format(
						"Process ended. elapsedTime: %d", 
						elapsedTime											
						));
	}

	public static Double getCollectionTimeofAnIteration (String garbageLogFile) throws Exception{
		Double collectionTimeofAnIteration = 0.0;
		BufferedReader br = new BufferedReader(new FileReader(garbageLogFile));
		String line;
		while ((line = br.readLine()) != null) {
			int timeStartIndex = line.indexOf(" secs") - 10;
			collectionTimeofAnIteration += Double.parseDouble(line.substring(timeStartIndex, timeStartIndex+9));	 
		}
		br.close();

		return collectionTimeofAnIteration;
	}

	public static void logResult(	
			String resultFile,
			int listSize,
			int sortCount,	
			ArrayList<CollectionResult> collectionTimes
			) throws Exception {

		PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(resultFile, true)));
		file.println(String.format("%d %d %f %f %f %f %f %f %f %f %f %f %f %f", 				
				listSize,
				sortCount,		

				collectionTimes.get(0).Average,		
				collectionTimes.get(0).Min,	
				collectionTimes.get(0).Max,	

				collectionTimes.get(1).Average,
				collectionTimes.get(1).Min,
				collectionTimes.get(1).Max,

				collectionTimes.get(2).Average,
				collectionTimes.get(2).Min,
				collectionTimes.get(2).Max,

				collectionTimes.get(3).Average,
				collectionTimes.get(3).Min,
				collectionTimes.get(3).Max
				));

		file.close();
	}

	public static void printResultHeader() throws Exception {

		PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("Results.txt", true)));
		file.println(String.format("%s %s %s %s %s %s", 				
				"ListSize",
				"SortCount",
				"ExecutionIteration",
				"ArrayList_Merge",
				"ArrayList_Quick",
				"LinkedList_Merge",
				"LinkedList_Quick"
				));

		file.close();
	}

}
