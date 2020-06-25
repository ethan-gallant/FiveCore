package dev.excl.mc.fivecore.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;

@CommandAlias("bal|balance")

public class Balance {

        @Default
        @Subcommand("show")
        @Syntax("<+tag> [page] or [page]")
        @CommandCompletion("@residencelist")
        @Description("Lists all of your or another players residences.")
        public static void onList(Player player, String[] args) {
            if (args.length == 0) {
                EmpireUser user = EmpireUser.getUser(player);

                Util.sendMsg(player, "&bYou currently have &a" + user.numRes +
                        "&b/&a" + user.maxRes + "&b Residences.");

                Residences.listGlobalResidences(player);
            } else {
                if (args[0].startsWith("+")) {
                    int page = (args.length == 2 && NumUtil.isInteger(args[1])) ? Integer.parseInt(args[1]) : 1;
                    TagManager.listTaggedResidences(player, args[0].substring(1), page);
                } else {
                    Residences.listGlobalResidences(player, args[0]);
                }
            }
        }

        @HelpCommand
        public static void onHelp(CommandSender sender, CommandHelp help) {
            sendMsg(sender, heading("Residences Help"));
            help.showHelp();
        }

}
