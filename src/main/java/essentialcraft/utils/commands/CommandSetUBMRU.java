package essentialcraft.utils.commands;

import java.util.Collections;
import java.util.List;

import essentialcraft.utils.common.ECUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandSetUBMRU extends CommandBase {

	@Override
	public String getName() {
		return "setubmru";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/setubmru <player> <amount>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		int var3 = parseInt(args.length == 1 ? args[0] : args[1], 0);
		EntityPlayerMP player = args.length == 1 ? getCommandSenderAsPlayer(sender) : getPlayer(server, sender, args[0]);
		ECUtils.getData(player).modifyUBMRU(var3);
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender par1ICommandSender, String[] par2ArrayOfStr, BlockPos pos) {
		return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] s, int par1) {
		return par1 == 0;
	}
}
