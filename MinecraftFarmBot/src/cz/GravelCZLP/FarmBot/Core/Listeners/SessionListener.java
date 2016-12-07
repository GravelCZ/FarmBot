package cz.GravelCZLP.FarmBot.Core.Listeners;

import java.util.logging.Level;

import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;

import cz.GravelCZLP.FarmBot.Core.Bot;

public class SessionListener extends SessionAdapter {

	protected final Bot bot;
	
	public SessionListener(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public void disconnected(DisconnectedEvent event) {
		String why = event.getReason();
		bot.getLogger().log(Level.INFO, "Disconnected: {0}", why);
	}
	
	public void onJoin(GameProfile profile) {
		if (bot.isAutoRegister()) {
			bot.sendMessage("/register bot123 bot123");
			bot.sendMessage("/login bot123");
		}
	}
}
