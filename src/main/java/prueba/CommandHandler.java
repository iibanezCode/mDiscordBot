package prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CommandHandler {

    private static Map<String, Command> commandMap = new HashMap<>();

    private static IChannel channel;


    static {

        commandMap.put("test", (event, args) -> {
            BotUtils.sendMessage(event.getChannel(), "You ran the test command");
        });

        commandMap.put("setChannel", (event, args) -> {
            channel = event.getChannel();
            channel.sendMessage("Alerts activadas en el canal: " + channel.getName());
        });

        commandMap.put("unSetChannel", (event, args) -> {
           if(channel!=null) {
               channel = null;
               event.getChannel().sendMessage("Desactivadas alerts");
           }
        });

        commandMap.put("alertStatus", (event, args) -> {
            if (channel != null) {
                event.getChannel().sendMessage("Alertas activadas en el canal: " + channel.getName());
            } else {
                event.getChannel().sendMessage("Alerts desactivadas, usa el comando 'setChannel' para que inicie en el canal de voz especificado");
            }
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
    }


    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] argArray = event.getMessage().getContent().split(" ");

        if (argArray.length == 0)
            return;

        if (!argArray[0].startsWith(BotUtils.BOT_PREFIX))
            return;

        String commandStr = argArray[0].substring(1);

        List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
        argsList.remove(0); // Remove the command


        if (commandMap.containsKey(commandStr))
            commandMap.get(commandStr).runCommand(event, argsList);

    }

    @Scheduled(fixedRate = 10000)
    public void sendKzarkaAlert() {
        if (channel != null) {
            BotUtils.sendMessage(channel, "Kzarka!!!");
        }
    }


}