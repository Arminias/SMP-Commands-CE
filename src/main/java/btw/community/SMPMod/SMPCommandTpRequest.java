package btw.community.SMPMod;

import net.minecraft.src.*;

//a player sends a teleport request by typing /tpa [playername]

public class SMPCommandTpRequest extends CommandBase
{

    public String getCommandName()
    {
        return "tpa";
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] arguments)
    {
        //TESTER VVV
//        System.out.println("While processing command, tpa enabled? "+SMPMod.isTpaEnabled);
//        System.out.println("While processing command, tpa enabled? "+SMPMod.getInstance().getTpaEnabled());
//        System.out.println("While processing command, tpa enabled? "+SMPMod.getInstance().getTpaEnabled());

        if (arguments.length < 1)
        {
            throw new WrongUsageException("Try /tpa [playername]", new Object[0]);
        }
        else if (!SMPMod.getInstance().getTpaEnabled())
        {
            throw new WrongUsageException("This command is disabled.", new Object[0]);
        }
        else
        {
            EntityPlayerMP teleportingPlayer;
            //gets the request sender (the person sending the message) as a EntityPlayerMP object
            teleportingPlayer = getCommandSenderAsPlayer(sender);

            if (teleportingPlayer == null)
            {
                throw new PlayerNotFoundException();
            }

            //gets the target of the tp request
            EntityPlayerMP targetPlayer = func_82359_c(sender, arguments[arguments.length - 1]);

            if (targetPlayer == null)
            {
                throw new PlayerNotFoundException();
            }

            if (targetPlayer.worldObj != teleportingPlayer.worldObj)
            {
                notifyAdmins(sender, "commands.tp.notSameDimension", new Object[0]);
                return;
            }

            String targetPlayerName = targetPlayer.getEntityName();

            ((EntityPlayerMPAccessor)teleportingPlayer).setTpaRequestName(targetPlayer.getEntityName());
            teleportingPlayer.addChatMessage("You sent a teleport request to "+targetPlayerName+".");
            targetPlayer.addChatMessage(teleportingPlayer.getEntityName()+" sent you a teleport request.");

        }
    }
}
