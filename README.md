#### Java Version: JDK 21.0.1 

## Project Overview:
This Java-based application is designed for analysing sentiments expressed in tweets using virtual threads to handle large volumes of tweet data efficiently. The application allows a user to choose one  or more lexicons to assess whether the overall sentiment in a tweet is positive, negative, or neutral. 
  

## Download and Setup:

- Obtain the project's zip file and extract its contents to a desired location. 

- Open a terminal or command prompt. 

- Navigate to the project's src folder. 

- Compile the Java files using the command: javac ie/atu/sw/*.java 

- Run the application: java ie.atu.sw.Runner (from the src directory). 

#### If running as a JAR file: 

- In the terminal, navigate to the project folder and run: **java -jar  SentimentAnalysisWithVirtualThreads.jar** 



## Key Features 

- Concurrent Processing: Uses Java virtual threads to process tweets and lexicons simultaneously and quickly. 
- Custom File Paths: Users can set their own paths for tweets, lexicons, and output.
- Lexicon Analysis: Uses different lexicons to accurately score sentiments to determine if a tweet has a positive, negative or neutral undertone.  
- Easy-to-Use Interface: Simple console-based interface for smooth user interaction 
- Output Management: Saves analysis results in a user-created output directory.
- Error Handling: Effectively manages file reading/writing and checks valid paths.

## Additional Functions 

➢ Instant Report: Provides immediate sentiment analysis results for each tweet.

➢ Parsing a single text file or a whole directory: Users have the option of using a single text file 
or a whole directory of lexicons and tweets.

➢ In-Depth Sentiment Details: Analyses and reports the overall sentiment of tweets.

➢ Simple Options Menu: Users can simply view their saved settings.

➢ Handles Large Data: Efficiently processes large volumes of tweets and lexicons. 

➢ Data Accuracy: Ensures tweet data is processed correctly and consistently.
