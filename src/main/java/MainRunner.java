import sx.blah.discord.api.IDiscordClient;

public class MainRunner {

    public static void main(String[] args){

       /* if(args.length != 1){
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }*/

        IDiscordClient cli = BotUtils.getBuiltDiscordClient("NDY4ODI2NDgwMDc2MzkwNDIx.Di-38g.7kmpOZT1E7eTzyDsJHbHTOsJMPM");

        /*
        // Commented out as you don't really want duplicate listeners unless you're intentionally writing your code
        // like that.
        // Register a listener via the IListener interface
        cli.getDispatcher().registerListener(new IListener<MessageReceivedEvent>() {
            public void handle(MessageReceivedEvent event) {
                if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "test"))
                    BotUtils.sendMessage(event.getChannel(), "I am sending a message from an IListener listener");
            }
        });
        */

        cli.getDispatcher().registerListener(new MyEvents());


        cli.login();


    }

}
