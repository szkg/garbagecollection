package szkg.processManager;

import java.io.IOException;

import szkg.algorithms.sort.SorterType;


public class ProcessCreator {

	public static void main(String[] args)  throws IOException, InterruptedException {

		String jarName = args[0];
		int iterationCount = 10;

		for(int iteration = 0; iteration < iterationCount; iteration++){
			for(SorterType algorithm : SorterType.values()){

				Thread.sleep(10000);

				Process process = Runtime.getRuntime().exec(String.format(
						"java -Xloggc:Logs/%d_%d.txt -jar %s %d %d %d",
						algorithm.getValue(),
						iteration,
						jarName,
						100,
						algorithm.getValue(),
						1000
						));

				process.waitFor();		

			}
		}
	}

}
