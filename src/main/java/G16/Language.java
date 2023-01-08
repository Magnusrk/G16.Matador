package G16;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Language {
    //Get path to the package LanguagePacks. The LanguagePacks contains files (or "language packs") used in translating the program.
    private static final String languagePackPath = "LanguagePacks";
    private static final String languagePackDelimiter = ";"; //Delimiter between key and value in language pack files.
    private static final String languagePackExtension = ".csv";

    private static final Map<String, String> dictionary = new HashMap<>(); //Map a key to a value to create a 'dictionary'.

    private static String currentLanguagePack = "da";


    /**
     * Set language pack
     * @param pack with the name of the language pack to be used.
     * The name of a language pack is its filename without the extension.
     */
    public static void setLanguage(String pack){
        try {
            //Reads file from resources folder
            InputStream languagePack = Objects.requireNonNull(Main.class.getClassLoader()
                    .getResourceAsStream(languagePackPath + "/" + pack + ".csv"));

            currentLanguagePack = pack;
            initializeLanguagePack(languagePack);
        } catch (NullPointerException e){
            System.out.println("Couldn't find language pack '" + pack+languagePackExtension + "'");

        }
    }

    /**
     * Get string from language pack
     * @param key
     * The method will return a string corresponding to the given key in the set language pack.
     * @return String object.
     */
    public static String getString(String key){
        if(dictionary.isEmpty())
            setLanguage(currentLanguagePack);
        return dictionary.getOrDefault(key, "STRING-NOT-FOUND");

    }



    /**
     * Set language pack
     * @param pack with the name of the language pack to be used.
     * The name of a language pack is its filename without the extension.
     */
    public static void SetLanguage(String pack){
        try {
            //Reads file from resources folder
            InputStream languagePack = Objects.requireNonNull(Main.class.getClassLoader()
                    .getResourceAsStream(languagePackPath + "/" + pack + ".csv"));

            currentLanguagePack = pack;
            initializeLanguagePack(languagePack);
        } catch (NullPointerException e){
            System.out.println("Couldn't find language pack '" + pack+languagePackExtension + "'");

        }
    }

    public static String GetString(String key) {
        if(dictionary.isEmpty())
            SetLanguage(currentLanguagePack);
        return dictionary.getOrDefault(key, "STRING-NOT-FOUND");
    }

    /**
     * Initialize current language pack
     * Reads the language pack file and maps the keys and values to the dictionary variable.
     */
    private static void initializeLanguagePack(InputStream languagePack){
        dictionary.clear();

        Scanner fileReader = new Scanner(languagePack);
        int lineNumber = 1;
        //Reads each line of the language pack
        while (fileReader.hasNextLine()){
            String line = fileReader.nextLine();
            String[] entry = line.split(languagePackDelimiter);
            //If the line is not empty and the leading character is not '#' (marking a comment) add it to the dictionary.
            //If the leading character is not '#' and does not contain the delimiter, write and error that the corresponding line is invalid.
            if(line.length() > 0){
                if (entry.length == 2 && line.charAt(0) != '#'){
                    dictionary.put(entry[0], entry[1]);
                } else if (line.charAt(0) != '#'){
                    System.out.println(currentLanguagePack + languagePackExtension + ": Line " + lineNumber +": Invalid line");
                }
            }
            lineNumber++;
        }
    }
}
