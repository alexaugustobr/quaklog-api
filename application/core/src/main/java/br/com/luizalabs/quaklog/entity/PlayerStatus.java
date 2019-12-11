package br.com.luizalabs.quaklog.entity;

import br.com.luizalabs.quaklog.entity.vo.ConnectStatus;
import br.com.luizalabs.quaklog.entity.vo.GameTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerStatus {

    private final GameTime time;
    private final ConnectStatus status;

    static PlayerStatus newConnectedTime(GameTime time) {
        return new PlayerStatus(time, ConnectStatus.CONNECTED);
    }

    static PlayerStatus newDisconnectedTime(GameTime time) {
        return new PlayerStatus(time, ConnectStatus.DISCONNECTED);
    }

    static PlayerStatus newBeginTime(GameTime time) {
        return new PlayerStatus(time, ConnectStatus.BEGIN);
    }
}