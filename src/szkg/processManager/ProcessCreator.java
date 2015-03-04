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
				1000,

				1,
				1,
				1,

				10000000,
				10000000,
				1
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

		String[] collectors = new String[]{
				"UseSerialGC",
				"UseParallelGC",
				"UseG1GC"
		};


		for(int collectorIndex = 0; collectorIndex < collectors.length; collectorIndex++){
			for(int executionIteration = 0; executionIteration < executionIterationCount; executionIteration++){

				for(int sortCount = sortCountStart; sortCount <= sortCountEnd; sortCount = sortCount + sortCountIncrement){
					for(int listSize = listSizeStart; listSize <= listSizeEnd; listSize = listSize + listSizeIncrement){					

						ArrayList<Double> collectionTimes = new ArrayList<Double>(4);
						ArrayList<Double> executionTimes = new ArrayList<Double>(4);

						for(ListType listType : ListType.values()){						
							for(SorterType algorithm : new SorterType[]{ SorterType.Merge, SorterType.Quick }){

								String garbageLogFile = String.format("Logs%s%d_%d_%d_%d_%d_%d.txt",
										File.separator,
										collectorIndex,
										algorithm.getValue(),
										listType.getValue(),
										listSize,
										sortCount,									
										executionIteration);

								String stopwatchFile = String.format("Logs%ss%d_%d_%d_%d_%d_%d.txt",										
										File.separator,
										collectorIndex,
										algorithm.getValue(),
										listType.getValue(),
										listSize,
										sortCount,									
										executionIteration);							

								runProcess(
										jarName,
										garbageLogFile,
										stopwatchFile,
										collectors[collectorIndex],
										algorithm,
										listType,
										listSize,
										sortCount,									
										executionIteration									
										);


								Double collectionTimeofAnIteration = getCollectionTimeofAnIteration(garbageLogFile);

								collectionTimes.add(collectionTimeofAnIteration);

								Double executionTimeofAnIteration = getExecutionTimeofAnIteration(stopwatchFile);

								executionTimes.add(executionTimeofAnIteration);
							}						

						}

						logResult(		
								resultFile,
								collectors[collectorIndex],
								listSize,
								sortCount,	
								collectionTimes,
								executionTimes
								);


					}
				}
			}
		}
	}

	public static void runProcess(
			String jarName,
			String garbageLogFile,
			String stopWatchLogFile,
			String collector,
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



		Process process = Runtime.getRuntime().exec(String.format(
				"java -Xloggc:%s -XX:+%s -jar %s %d %d %d %d %s",
				garbageLogFile,
				collector,
				jarName,
				algorithm.getValue(),
				listType.getValue(),
				listSize,
				sortCount,
				stopWatchLogFile
				));

		process.waitFor();	


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

		return collectionTimeofAnIteration * 1000;
	}

	public static Double getExecutionTimeofAnIteration (String stopWatchLogFile) throws Exception{
		Double executionTimeofAnIteration = 0.0;
		BufferedReader br = new BufferedReader(new FileReader(stopWatchLogFile));
		String line;
		while ((line = br.readLine()) != null) {
			executionTimeofAnIteration += Double.parseDouble(line);	 
		}
		br.close();

		return executionTimeofAnIteration;
	}

	public static void logResult(	
			String resultFile,
			String collector,
			int listSize,
			int sortCount,	
			ArrayList<Double> collectionTimes,
			ArrayList<Double> executionTimes
			) throws Exception {

		PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(collector + "_" + resultFile, true)));
		file.println(String.format("%d %d %f %f %f %f %f %f %f %f", 
				
				listSize,
				sortCount,		

				executionTimes.get(0),		
				collectionTimes.get(0),	

				executionTimes.get(1),
				collectionTimes.get(1),

				executionTimes.get(2),
				collectionTimes.get(2),

				executionTimes.get(3),
				collectionTimes.get(3)
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
