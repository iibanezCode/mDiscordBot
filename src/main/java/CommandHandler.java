import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;


import java.util.*;

public class CommandHandler {

    private static Map<String, Command> commandMap = new HashMap<>();

    static {

        commandMap.put("testcommand", (event, args) -> {
            BotUtils.sendMessage(event.getChannel(), "You ran the test command with args: " + args);
        });

        commandMap.put("ping", (event, args) -> {
            BotUtils.sendMessage(event.getChannel(), "pong");
        });

        commandMap.put("joinvoice", (event, args) -> {

            IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();

            if (userVoiceChannel == null)
                return;

            userVoiceChannel.join();

        });

        commandMap.put("leavevoice", (event, args) -> {

            IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild()).getChannel();

            if (botVoiceChannel == null)
                return;

            botVoiceChannel.leave();

        });
        commandMap.put("commandList", (event, args) -> {

            ArrayList<String> list = new ArrayList<String>();
            for (Map.Entry<String, Command> entry : commandMap.entrySet()) {
                list.add(entry.getKey());
            }
            EmbedBuilder builder = new EmbedBuilder();
            for (String s : list) {
                builder.appendField(s, s, false);
            }
            builder.withAuthorName("ChronoBot!");
            builder.withFooterText("Varo grieffer!");

            RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));

        });
    }



    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){

        String[] argArray = event.getMessage().getContent().split(" ");

        if(argArray.length == 0)
            return;

        if(!argArray[0].startsWith(BotUtils.BOT_PREFIX))
            return;

        String commandStr = argArray[0].substring(1);

        List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
        argsList.remove(0); // Remove the command


        if(commandMap.containsKey(commandStr))
            commandMap.get(commandStr).runCommand(event, argsList);

    }

}