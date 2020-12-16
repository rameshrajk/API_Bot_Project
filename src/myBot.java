import org.jibble.pircbot.PircBot;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//my IRC bot, uses PircBot library, contains main class, onMessage analyzes chat input and sends back appropriate message
public class myBot extends PircBot {
    static final String server = "irc.freenode.net";//irc server
    static final String channel = "#APIBroject";//irc channel
    static final String name = "myBrodie";//bot name
    static final String defaultCity = "75080";//def city richardson
    static final Pattern regex = Pattern.compile("(\\d{5})");//to find a 5-digit zipcode
    
    //constr
    public myBot() {
        setName(name);
    }
    
    //read message from channel
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        message = message.toLowerCase();
        //WEATHER STUFF
        //user says weather
        if (message.contains("weather")) {
            //if can't find city, uses default city
            String city = defaultCity;
            //split message
            String[] words = message.split(" ");
            //if message is 2 words
            if (words.length == 2) {
                //fig out which word is the city
                if (words[0].equals("weather")) {
                    city = words[1];
                } else {
                    city = words[0];
                }
            //for messages longer than 2 words
            } else {
            	//find zipcode
                Matcher matcher = regex.matcher(message);
                if (matcher.find()) {
                    city = matcher.group(1);
                } else {
                    //if not even zipcode found, tell user that using default city
                    sendMessage(channel, "City not found. Assuming Richardson.");
                }
            }
            
            try {
            	String temp = APICall.weatherCall(city);
                //if request failed
                if (temp == null) {
                	sendMessage(channel, "Error: Please try again");
                }
                //output temp
                sendMessage(channel, temp);
            } catch (Exception e) {
            	//
            }
        }
        
        //JOKE TING
        if (message.contains("joke")) {
        	try {
            	String joke = DadAPICall.dadJokeCall();
                // If the request failed
                if (joke == null) {
                	sendMessage(channel, "Sorry, encountered an issue with icanhazdadjoke API");
                }
                //output joke
                sendMessage(channel, joke);
            } catch (Exception e) {
            	//
            }
        }
    }

    public static void main(String[] args) {
        //my bot
        myBot bot = new myBot();
        bot.setVerbose(true);

        try {
            //connect to server
            bot.connect(server);
        } catch (Exception e) {
            //if failed to connect
            System.out.println("Could not connect to " + server);
        } finally {
            //when connected join server channel
            bot.joinChannel(channel);
            bot.sendMessage(channel, "Hi! I am " + name + "! You can ask me the weather or to tell you a joke!");
            bot.sendMessage(channel, "Ask for weather by typing a location/zip code and including the keyword 'weather' in your message.");
            bot.sendMessage(channel, "Ask for a joke by including the keyword 'joke' in your message.");
        }
    }

}