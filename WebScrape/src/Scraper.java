import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Scraper{

public static void main(String[] args){
	try {
	Scrape();
	}
	catch (MalformedURLException e) {
		
	}
	catch (IOException e) {
		
	}

}
	


	public static void Scrape() throws MalformedURLException, IOException {
	String id = "";
	String line = "";
	List<String> info = new ArrayList<String>();
	boolean searching = true;		//Tracks program status

	URL infoPage;
		
		
	while (searching) {
		
		System.out.print("Enter id: ");
		id = ReadFromConsole();
		//get ID from user
		
		
			infoPage = new URL("http://www.ecs.soton.ac.uk/people/" + id);
			BufferedReader pageSource = new BufferedReader(
				new InputStreamReader(infoPage.openStream()));
				//Create URl and open stream
			line = GetLineFromStream(pageSource, 96);
			info = PullInfo(line);
			
			for (int i = 0; i < info.size(); i++) {
				System.out.println(info.get(i));
			}
			
		}
		
	}

	public static List<String> PullInfo(String Line) {
		List<String> info = new ArrayList<String>();
		
		try {
		info.add("Name: " + returnRegexHTML("name", Line, ">", "<"));
		info.add("Tel number: " + returnRegexHTML("\\+44", Line, ">", "<"));
		info.add("jobTitle: " + returnRegexHTML("jobTitle", Line, ">", "<"));
		info.add("e-mail: " + returnRegexHTML("email", Line, ">", "<"));
		info.add("Website: " + returnRegexHTML("url", Line, "\"http", "\">"));
		} catch (IllegalStateException e) {
			info.add("No match found");
		}
		//etc,
		return info;
	}
	
	public static String returnRegexHTML(String REGEX, String stringToMatch, String startChar, String endChar) {
		String result = "";
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(stringToMatch);
		
		m.find();
		
		result = stringToMatch.substring(m.start());
		
		result = result.substring(result.indexOf(startChar) + 1, result.indexOf(endChar));

		return result;
	}
	
	public static String ReadFromConsole() {
		String input = "";
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));

		try {
		input = br.readLine();
		}
		catch(IOException e) {
			return "ex";
		}
		
		return input;
	}
	
	
	
	
	public static String GetLineFromStream(BufferedReader Source, int lineNum) throws IOException{

		String Line = "";
		lineNum -= 1;
		
		for(int x = 1; x<=lineNum; x++) {
			Source.readLine();
		}
		Line = Source.readLine();
		return Line;
	}
}
