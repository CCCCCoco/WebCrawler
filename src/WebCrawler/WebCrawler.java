package WebCrawler;
import java.util.ArrayList;
import java.util.Scanner;

public class WebCrawler {

    public static void main(String[] args) {
//        File recordFlie = new File("record.txt");
//        PrintWriter output = new PrintWriter(recordFlie);
        System.out.println("Enter a URL:");
        String url = new Scanner(System.in).next();
        webCrawler(url);
    }

    private static void webCrawler(String startURL){
        ArrayList<String> listOfPendingURLs = new ArrayList<>();
        ArrayList<String> listOfTraversedURLs = new ArrayList<>();

        listOfPendingURLs.add(startURL);
        while(!listOfPendingURLs.isEmpty() && listOfTraversedURLs.size() <= 100){
            String urlString = listOfPendingURLs.remove(0);
            if(!listOfTraversedURLs.contains(urlString)){
                listOfTraversedURLs.add(urlString);
                System.out.println("Crawl " + urlString);
                for (String subURL: getSubURLs(urlString)){
                    if(!listOfTraversedURLs.contains(subURL)){
                        listOfPendingURLs.add(subURL);
                    }
                }
            }
        }
    }

    private static ArrayList<String> getSubURLs(String urlString){
        ArrayList<String> list = new ArrayList<>();
        try {
            java.net.URL url = new java.net.URL(urlString);
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            while (input.hasNext()){
                String line = input.nextLine();
//                System.out.println(line);
                current = line.indexOf("https://",current);
                while (current > 0){
                    int endIndex = line.indexOf("\"",current);
                    if(endIndex > 0){
                        list.add(line.substring(current,endIndex));
                        current = line.indexOf("https://",endIndex);
                    }
                    else
                        current = -1;
                }
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return list;
    }
}