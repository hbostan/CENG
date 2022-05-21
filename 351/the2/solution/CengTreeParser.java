import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class CengTreeParser 
{	
	public static ArrayList<CengGift> parseGiftsFromFile(String filename) {
		ArrayList<CengGift> giftList = new ArrayList<CengGift>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = reader.readLine()) != null) {
			    //System.out.println(line);
				String[] parsed = line.split("\\|");
				CengGift gift = new CengGift(Integer.parseInt(parsed[0]), parsed[1], parsed[2], parsed[3]);
				giftList.add(gift);
			}
		} catch (Exception e) {

		}

		/*System.out.println("Input File Parsing");
		for (int i = 0; i < giftList.size(); i++)
		{
			CengGift gift = giftList.get(i);
			System.out.println(gift.fullName());
		}
        System.out.println("END Input file parsing");*/
		// You need to parse the input file in order to use GUI tables.
		// TODO: Parse the input file, and convert them into CengGifts
		
		return giftList;
	}
	
	public static void startParsingCommandLine() throws IOException
	{
		// TODO: Start listening and parsing command line -System.in-.
		// There are 4 commands:
		// 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
		// 2) add : Parse and create the gift, and call CengChristmasList.addGift(newlyCreatedGift).
		// 3) search : Parse the key, and call CengChristmasList.searchGift(parsedKey).
		// 4) print : Print the whole tree, call CengChristmasList.printTree().
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String [] parsed= null;
        String line = null;
        while(true)
        {
            line = reader.readLine();
            parsed = line.split("\\|");
            if(parsed[0].equalsIgnoreCase("quit"))
            {
                break;
            }
            else if (parsed[0].equalsIgnoreCase("add"))
            {
                /*System.out.println(parsed[0]);
                System.out.println(parsed[1]);
                System.out.println(parsed[2]);
                System.out.println(parsed[3]);
                System.out.println(parsed[4]);*/
                CengChristmasList.addGift(new CengGift(Integer.parseInt(parsed[1]),parsed[2],parsed[3],parsed[4]));
            }
            else if (parsed[0].equalsIgnoreCase("search"))
            {
                /*System.out.println(parsed[0]);
                System.out.println(parsed[1]);*/
                CengChristmasList.searchGift(Integer.parseInt(parsed[1]));
            }
            else if (parsed[0].equalsIgnoreCase("print"))
            {
                //System.out.println(parsed[0]);
                CengChristmasList.printTree();
            }
        }

		// Commands (quit, add, search, print) are case-insensitive.
	}
}
