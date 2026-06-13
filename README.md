Word Builder Game is an educational application based on Java Swing that focuses on 
developing vocabulary, accuracy in spelling and Cognitive abilities of the user by playing the 
interactive game on the desktop. The game gives the user a set of letters that he/she has to 
work with in each round and challenges him/her to create all valid English words possible 
with the letters given to him/her. The system checks against a dictionary file and makes sure 
unique and properly formed words are accepted only. Word-length-based scoring system 
encourages the user to sustain different word combinations. In this project, object-oriented 
programming, graphical user interface, file manipulation, collections framework, and event
driven programming in Java were used successfully. 

Working Details of the Game: 

1. Application Startup  
When the application starts, The primary JFrame window is developed. Labels, buttons, text 
fields and panels are GUI elements that are initialized. The dictionary file (dictionary.txt) is 
loaded into memory. The preparation of the first round is automatic.  
2. Dictionary Loading 
The dictionary is saved in a text file where each word is typed in a different line. With the 
help of BufferedReader and FileReader, all the words are read and placed in an Array List. 
Every character is turned to small letters in order to eliminate the problem of case
senescence. This makes it easy to validate the words put in by the users. 
3. Round Preparation  
All the Rounds have a constant sequence of letters (e.g., ATER, ALERT). The system will 
check each word on the dictionary to learn whether it can be made with the available letters. 
A HashSet is used to hold valid words known as possibleWords.  
Logic Used: Every letter of the word should be present in the provided set of letters and nom 
alphabet will be reused. 
4. Tile Display 
The alphabets are displayed in tiles. Distribution of tiles is optimized at the center of the 
window so that it is more visually clear.  
5. Word Entry and Validation Upon typing a word and clicking SUBMIT  
The system checks the existence of the user typed word in possible Words. Also, if the word 
is entered earlier or not.  
• If valid then the word is inserted to found Words. The word is represented in the panel 
of Words Found. Score is updated.  
• If invalid or repeated then there is a message dialog of invalid or already used!  
6. Scoring Mechanism  
Calculation of scores is done based on word length.   
• Formula: 𝑆𝑐𝑜𝑟𝑒 = 𝐿𝑒𝑛𝑔𝑡ℎ 𝑜𝑓 𝑤𝑜𝑟𝑑 × 10  
• Example: Word: tear and Length: 4 then Score: 40  
This will motivate the users to seek longer and more complicated words.  
7. Give Up Feature Upon clicking the GIVE UP button 
In a dialog box, all the valid words of the current round are presented. This assists the users to 
learn the forgotten words and enhances vocabulary.  
8. Next Round Handling Clicking NEXT ROUND 
Changes the game to the following letter set. Clears previous round data. Round off round 
numbers and possible words. If no rounds remain then a message of a Game Completed! is 
shown.  
9. Quit Functionality  
The application can be safely ended by clicking the QUIT that uses System.exit(0).
