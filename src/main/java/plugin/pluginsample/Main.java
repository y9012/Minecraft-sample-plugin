package plugin.pluginsample;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();


        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("setLevel").setExecutor(new SetLevelCommand(this));
        getCommand("allSetLevel").setExecutor(new AllSetLevelCommand());

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();
        Location playerLocation = player.getLocation();

        world.getBlockAt(new Location(world,
            playerLocation.getX()+3,
            playerLocation.getY(),
            playerLocation.getZ())).setType(Material.DARK_OAK_WOOD);


    }



    /**
     * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
     *
     * @param e イベント
     */
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
        Player player = e.getPlayer();
        World world = player.getWorld();

        // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // 花火オブジェクトが持つメタ情報を取得。
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // メタ情報に対して設定を追加したり、値の上書きを行う。
        // 今回は青色で星型の花火を打ち上げる。
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(Color.YELOOW)
                .with(Type.STAR)
                .withFlicker()
                .build());
        fireworkMeta.setPower(0);

        // 追加した情報で再設定する。
        firework.setFireworkMeta(fireworkMeta);
    }

}