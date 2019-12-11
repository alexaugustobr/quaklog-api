package br.com.luizalabs.quaklog.entity;

import br.com.luizalabs.quaklog.entity.vo.ConnectStatus;
import br.com.luizalabs.quaklog.entity.vo.GameTime;
import br.com.luizalabs.quaklog.entity.vo.Mod;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ToString
public class PlayerInGame extends PlayerKiller implements CanDead {

    @Getter
    private final Integer id;
    @Getter
    private String name;

    private final List<Item> items;
    private final List<PlayerStatus> status;
    private Map<String, String> parameters;

    public PlayerInGame(GameTime time, Integer id) {
        this.id = id;
        this.items = new ArrayList<>();
        status = new ArrayList<>();
        connect(time);
    }

    public List<PlayerStatus> getStatus() {
        return Collections.unmodifiableList(status);
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }


    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public ConnectStatus getConnectStatus() {
        return status.get(status.size() - 1).getStatus();
    }

    public void changeInfos(String playerName, Map<String, String> parameters) {
        this.name = playerName;
        this.parameters = parameters;
    }

    private void connect(GameTime time) {
        status.add(PlayerStatus.newConnectedTime(time));
    }

    public void begin(GameTime timeBegin) {
        status.add(PlayerStatus.newBeginTime(timeBegin));
    }

    public void disconnect(GameTime timeBegin) {
        status.add(PlayerStatus.newDisconnectedTime(timeBegin));
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void deadBy(GameTime gameTime, Player player, Mod mod) {
        addHistory(KillHistory.deadBy(gameTime, player, mod));
        if (isWorld(player)) {
            decrementKill();
        }
    }

    private boolean isWorld(Player player) {
        return player.getId().equals(World.WORLD_ID);
    }

}