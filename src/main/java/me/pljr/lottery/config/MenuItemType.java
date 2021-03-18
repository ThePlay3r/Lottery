package me.pljr.lottery.config;

import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.xseries.XMaterial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public enum MenuItemType {
    MAIN_HEAD(new ItemBuilder(XMaterial.PLAYER_HEAD).withName("&e{name}").create()),
    MAIN_BACKGROUND_1(new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).withName("&0").create()),
    MAIN_BACKGROUND_2(new ItemBuilder(XMaterial.YELLOW_STAINED_GLASS_PANE).withName("&0").create()),
    MAIN_CURRENT_MONEY(new ItemBuilder(XMaterial.CHEST).withName("&e&lCURRENT LOTTERY").withLore("&8► &fMoney in lottery: &b{amount}").create()),
    MAIN_INSTRUCTIONS(new ItemBuilder(XMaterial.WRITABLE_BOOK).withName("&e&lINFORMATION").withLore("&fDo you believe in your luck?", "&fThen buy some tickets and win!", "&fThe more tickets you have, the", "&fbigger chance of winning you have!", "", "&eAmount you win, is the sum of all", "&etickets bought.").create()),
    MAIN_BUY(new ItemBuilder(XMaterial.GOLD_INGOT).withName("&e&lBUY TICKET").withLore("&8► &fClick to buy tickets!", "", "&b&lLeft &bClick &f1 Ticket", "&b&lMiddle &bClick &f10 Tickets", "&b&lRight &bClick &f30 Tickets").create()),
    MAIN_LIST(new ItemBuilder(XMaterial.PAPER).withName("&e&lLIST OF BUYERS").withLore("&8► &fClick to see all players", "&fparticipating in current lottery.").create()),
    MAIN_HELP(new ItemBuilder(XMaterial.BOOK).withName("&e&lINFORMATION").withLore("&a&lLottery Help", "", "&e/lottery &8» &fOpens the main GUI.", "&e/lottery help &8» &fDisplays this message.", "&e/lottery buy <amount> &8» &fBuys tickets, if you have enough money.", "&e/lottery info &8» &fDisplays information about current lottery.", "&e/lottery list &8» &fOpens GUI with current players in lottery.", "&e/lottery stats <player> &8» &fDisplays player's statistics.").create()),
    MAIN_TIME_LEFT(new ItemBuilder(XMaterial.CLOCK).withName("&e&lTIME TO NEXT DRAW").withLore("&8► &fTime to next draw is:", "&b{time}").create()),
    MAIN_ADMIN(new ItemBuilder(XMaterial.REDSTONE_TORCH).withName("&c&lADMIN FUNCTIONS").withLore("&bWhoa&f, it seems you are an", "&fadministrator!", "", "&eClick to see some cool stuff!").create()),

    LIST_HEADS(new ItemBuilder(XMaterial.PLAYER_HEAD).withName("{name}").withLore("&8► &fClick to see &fplayer's statistics.").create()),
    LIST_BACKGROUND_1(new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).withName("&0").create()),
    LIST_BACKGROUND_2(new ItemBuilder(XMaterial.YELLOW_STAINED_GLASS_PANE).withName("&0").create()),
    LIST_BACK(new ItemBuilder(XMaterial.REDSTONE).withName("&c&lBACK").withLore("&8► &fClick to go to the main menu.").create()),

    CONFIRM_BACKGROUND(new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).withName("&0").create()),
    CONFIRM_CONFIRM_1(new ItemBuilder(XMaterial.LIME_STAINED_GLASS_PANE).withName("&a&lCONFIRM").withLore("&8► &fClick to confirm.").create()),
    CONFIRM_CONFIRM_2(new ItemBuilder(XMaterial.LIME_STAINED_GLASS).withName("&a&lCONFIRM").withLore("&8► &fClick to confirm.").create()),
    CONFIRM_INFORMATION(new ItemBuilder(XMaterial.WRITABLE_BOOK).withName("&e&lARE YOU SURE?").withLore("&fAre you sure, you want to continue", "&fwith the transaction?").create()),
    CONFIRM_DECLINE_1(new ItemBuilder(XMaterial.RED_STAINED_GLASS_PANE).withName("&c&lDECLINE").withLore("&8► &fClick to decline.").create()),
    CONFIRM_DECLINE_2(new ItemBuilder(XMaterial.RED_STAINED_GLASS).withName("&c&lDECLINE").withLore("&8► &fClick to decline.").create()),
    CONFIRM_BACK(new ItemBuilder(XMaterial.REDSTONE_LAMP).withName("&c&lBACK").withLore("&8► &fClick to go to the main menu.").create()),

    PLAYER_HEAD(new ItemBuilder(XMaterial.PLAYER_HEAD).withName("&e{name}").create()),
    PLAYER_BACKGROUND_1(new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).withName("&0").create()),
    PLAYER_BACKGROUND_2(new ItemBuilder(XMaterial.YELLOW_STAINED_GLASS_PANE).withName("&0").create()),
    PLAYER_EARNINGS(new ItemBuilder(XMaterial.GOLD_INGOT).withName("&e&lTOTAL EARNINGS").withLore("&8► &f{amount}").create()),
    PLAYER_BIGGEST_WIN(new ItemBuilder(XMaterial.EMERALD).withName("&e&lBIGGEST WINNINGS").withLore("&8► &f{amount}").create()),
    PLAYER_TICKETS(new ItemBuilder(XMaterial.PAPER).withName("&e&lTICKETS BOUGHT").withLore("&8► &f{amount} in current lottery.").create()),
    PLAYER_BACK(new ItemBuilder(XMaterial.REDSTONE_LAMP).withName("&c&lBACK").withLore("&8► &fClick to go to the main menu.").create());

    private static HashMap<MenuItemType, ItemStack> items;
    private final ItemStack defaultValue;

    MenuItemType(ItemStack defaultValue){
        this.defaultValue = defaultValue;
    }

    public static void load(ConfigManager config){
        items = new HashMap<>();
        FileConfiguration fileConfig = config.getConfig();
        for (MenuItemType menuItemType : values()){
            if (!fileConfig.isSet(menuItemType.toString())){
                config.setSimpleItemStack(menuItemType.toString(), menuItemType.defaultValue);
            }else{
                items.put(menuItemType, config.getSimpleItemStack(menuItemType.toString()));
            }
        }
        config.save();
    }

    public ItemStack get(){
        return items.getOrDefault(this, defaultValue);
    }
}
