import org.jibble.pircbot.*;

public class MyBotMain {
	public static void main(String [] args) throws Exception{
		
		MyBot bot = new MyBot();
		
		bot.setVerbose(true);
		
		bot.connect("irc.libera.chat");
		
		bot.joinChannel("#osmang");
		
		bot.sendMessage("#osmang", "Talk to me!");
		
	}
}
