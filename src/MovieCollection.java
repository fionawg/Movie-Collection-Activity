import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> cast;
    private ArrayList<String> genre;
    private ArrayList<Double> topRatings;
    private ArrayList<Integer> topRevenues;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        //creates cast list
        String[] c;
        cast = new ArrayList<String>();
        for (Movie movie : movies){
            String actors = movie.getCast();
            c = actors.split("\\|");
            for (String name : c){
                if (!cast.contains(name)){
                    cast.add(name);
                }
            }
        }
        //creates genre list and sorts it
        String[] genres;
        genre = new ArrayList<String>();
        for (Movie movie : movies){
            String actors = movie.getGenres();
            genres = actors.split("\\|");
            for (String name : genres){
                if (!genre.contains(name)){
                    genre.add(name);
                }
            }
        }
        for (int j = 1; j < genre.size(); j++)
        {
            String tempTitle = genre.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(genre.get(possibleIndex - 1)) < 0)
            {
                genre.set(possibleIndex, genre.get(possibleIndex - 1));
                possibleIndex--;
            }
            genre.set(possibleIndex, tempTitle);
        }
        //creates a list of ratings and sorts it
        ArrayList<Double> ratings = new ArrayList<Double>();
        for (Movie movie : movies){
            ratings.add(movie.getUserRating());
        }
        for (int j = 1; j < ratings.size(); j++)
        {
            double temp = ratings.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp > ratings.get(possibleIndex - 1))
            {
                ratings.set(possibleIndex, ratings.get(possibleIndex - 1));
                possibleIndex--;
            }
            ratings.set(possibleIndex, temp);
        }
        topRatings = new ArrayList<Double>();
        for (int i = 0; i < 50; i++){
            topRatings.add(ratings.get(i));
        }
        System.out.println(topRatings);
        //creates a list of revenues and sorts it
        ArrayList<Integer> revenues = new ArrayList<Integer>();
        for (Movie movie : movies){
            revenues.add(movie.getRevenue());
        }
        for (int j = 1; j < revenues.size(); j++)
        {
            int temp = revenues.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp > revenues.get(possibleIndex - 1))
            {
                revenues.set(possibleIndex, revenues.get(possibleIndex - 1));
                possibleIndex--;
            }
            revenues.set(possibleIndex, temp);
        }
        topRevenues = new ArrayList<Integer>();
        for (int i = 0; i < 50; i++){
            topRevenues.add(revenues.get(i));
        }
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        //asks user for name to search
        System.out.print("Enter a name: ");
        String name = scanner.nextLine();
        name = name.toLowerCase();
        ArrayList<String> results = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++)
        {
            String namee = cast.get(i);
            namee = namee.toLowerCase();
            if (namee.indexOf(name) != -1)
            {
                results.add(cast.get(i));
            }
        }
        //sort results
        for (int j = 1; j < results.size(); j++)
        {
            String tempTitle = results.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(results.get(possibleIndex - 1)) < 0)
            {
                results.set(possibleIndex, results.get(possibleIndex - 1));
                possibleIndex--;
            }
            results.set(possibleIndex, tempTitle);
        }
        //display to user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        //ask user to pick an actor/actress
        System.out.println("Pick an actor to see the movies they're in.");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String selectedName = results.get(choice - 1);
        //creates a list of movies that the actor was in and sorts it
        ArrayList<Movie> movieList = new ArrayList<Movie>(0);
        for (Movie movie : movies){
            if (movie.getCast().indexOf("|" + selectedName + "|") > 0){
                movieList.add(movie);
            }
        }
        sortResults(movieList);
        //prints out the movies and asks the user which one they want to learn about
        for (int i = 0; i < movieList.size(); i++){
            System.out.println(i + 1 + ". " + movieList.get(i).getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int k = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movieList.get(k - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String keyword = movies.get(i).getKeywords();
            keyword = keyword.toLowerCase();

            if (keyword.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        //prints out the list of genres and asks the user to pick one
        for (int i = 0; i < genre.size(); i++){
            System.out.println(i + 1 + ". " + genre.get(i));
        }
        System.out.println("Pick a genre to see a list of movies. ");
        System.out.print("Enter a number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        //finds all the movies with the genre and sorts the list
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        String selectedGenre = genre.get(choice - 1);
        for (Movie movie : movies){
            if (movie.getGenres().indexOf("|" + selectedGenre + "|") > 0){
                movieList.add(movie);
            }
        }
        sortResults(movieList);
        //prints out the movies and asks the user to choose a movie to learn more about
        for (int i = 0; i < movieList.size(); i++){
            System.out.println(i + 1 + ". " + movieList.get(i).getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int k = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movieList.get(k - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        //finds the movies that correspond to the ratings
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        ArrayList<Movie> copyMovies = new ArrayList<Movie>();
        for (Movie m : movies){
            copyMovies.add(m);
        }
        for (int i = 0; i < topRatings.size(); i++){
            for (int j = 0; j < copyMovies.size(); j++){
                if (copyMovies.get(j).getUserRating() == topRatings.get(i) && movieList.size() < 50){
                    movieList.add(copyMovies.get(j));
                    copyMovies.remove(j);
                    j--;
                }
            }
        }
        //prints out the list 1-50
        int count = 0;
        for (Movie movie : movieList){
            count++;
            System.out.println(count + ". " + movie.getTitle() + ": " + movie.getUserRating());
        }
        //asks the user which movie would they like to learn more about
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int k = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movieList.get(k - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();    }

    private void listHighestRevenue()
    {
        //finds the movies that correspond to the revenue
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        for (int i = 0; i < topRevenues.size(); i++){
            for (int j = 0; j < movies.size(); j++){
                if (movies.get(j).getRevenue() == topRevenues.get(i)){
                    movieList.add(movies.get(j));
                }
            }
        }
        //prints out the list 1-50
        int count = 0;
        for (Movie movie : movieList){
            count++;
            System.out.println(count + ". " + movie.getTitle() + ": " + movie.getRevenue());
        }
        //asks the user which movie would they like to learn more about
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int k = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movieList.get(k - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}