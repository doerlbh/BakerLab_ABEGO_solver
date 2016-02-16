// Copyright: Baihan Lin, Baker Lab, doerlbh@gmail.com
// Date: Oct 2015

import java.io.*;     // for File, FileNotFoundException
import java.util.*;   // for Scanner, List, Set, Collections

public class ABEGO_solver {

	public static void main(String[] args) throws IOException {	

		// open source file
		System.out.println("What is the name of the file containing ABEGO information? ");
		Scanner console1 = new Scanner(System.in);
		String dataFile = console1.nextLine();

		System.out.println("What is the file containing list of the desired ABEGO Types?");
		Scanner console2 = new Scanner(System.in);
		String typeFile = console2.nextLine();

		System.out.println("What is the name of the result file?");
		Scanner console3 = new Scanner(System.in);
		String outFile = console3.nextLine();

		System.out.println("What is the path of the data folder?");
		Scanner console4 = new Scanner(System.in);
		String pathS = console4.nextLine();
		File path = new File(pathS);
		String dir = path.getPath();

		Map<Integer, ArrayList<String>> interestTypes = new TreeMap<Integer, ArrayList<String>>();
		List<String> dataList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();

		Scanner interestin = new Scanner(new File(dir, typeFile));
		int tinterest = 0;
		while (interestin.hasNextLine()) {
			String next = interestin.nextLine().trim();
			if (isInteger(next)) {
				tinterest = Integer.valueOf(next).intValue();
				interestTypes.put(tinterest, new ArrayList<String>());
			} else {
				interestTypes.get(tinterest).add(next);
			}
		}	

		Scanner datain = new Scanner(new File(dir, dataFile));
		while (datain.hasNextLine()) {
			dataList.add(datain.nextLine());
		}

		for (int i = 0; i < dataList.size()/2; i++) {
			if (ABEGOmatch(dataList.get(2*i), dataList.get(2*i + 1), interestTypes)) {
				resultList.add(dataList.get(2*i));
			}
		}

		File result = new File(dir, outFile);
		result.createNewFile(); 
		PrintStream output = new PrintStream(result);

		for (int r = 0; r < resultList.size(); r++) {
			output.print(resultList.get(r));
			output.println();
		}

		output.close();
		System.out.println("Finished!");

	}

	private static boolean ABEGOmatch(String ID, String ABEGO, Map<Integer, ArrayList<String>> interest) {
		int indexLen = ID.indexOf("_len_");
		//		System.out.println(indexLen + "---kajdshfjkahdsfjkhaksjdfhakls");
		//		System.out.println(ID.substring(indexLen + 5, indexLen + 6) + "---kajdshfjkahdsfjkhaksjdfhakls");

		int loop_length = Integer.parseInt(ID.substring(indexLen + 5, indexLen + 6));
		if (interest.containsKey(loop_length)) {
			for (int j = 0; j < interest.get(loop_length).size(); j++) {
				if (ABEGO.indexOf(interest.get(loop_length).get(j)) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		} catch(NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

}