import sx.blah.discord.api.IDiscordClient;

public class MainRunner {

    public static void main(String[] args){

       /* if(args.length != 1){
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }*/

        IDiscordClient cli = BotUtils.getBuiltDiscordClient("NDc3MDY4MTA1NDgzMjg4NTc3.Dk2weg.gTzcVQUKZB_7kDJVa9GUbOQL_gg");

        cli.getDispatcher().registerListener(new CommandHandler());


        cli.login();


    }

}
