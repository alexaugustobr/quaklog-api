package br.com.luizalabs.quaklog.entrypoint.rest;

import br.com.luizalabs.quaklog.configuration.SwaggerConfig;
import br.com.luizalabs.quaklog.entity.vo.GameUUID;
import br.com.luizalabs.quaklog.entrypoint.GameGetterEntryPoint;
import br.com.luizalabs.quaklog.entrypoint.dto.GameDTO;
import br.com.luizalabs.quaklog.entrypoint.dto.SimpleListGamesDTO;
import br.com.luizalabs.quaklog.entrypoint.mapper.GameMapper;
import br.com.luizalabs.quaklog.entrypoint.mapper.SimpleGamesMapper;
import br.com.luizalabs.quaklog.usecase.GameGetterUseCase;
import io.swagger.annotations.Api;
import lombok.val;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(RestConstants.PATH_GAME)
@Api(tags = SwaggerConfig.TAG_GAME_ENTRY_POINT)
class GameGetterRestEntryPoint implements GameGetterEntryPoint {

    private final GameGetterUseCase useCase;

    GameGetterRestEntryPoint(GameGetterUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    @GetMapping("/date/{date}")
    public SimpleListGamesDTO searchGameByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return SimpleGamesMapper.toDTO(useCase.getGamesByDate(date));
    }


    @GetMapping(value = "/{uuid}")
    public ResponseEntity<GameDTO> getGameByUUIDEndpoint(@PathVariable String uuid) {
        val game = getGameByUUID(uuid);
        return game == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(game);
    }

    @Override
    public GameDTO getGameByUUID(String uuid) {
        val game = useCase.getGameByUUID(GameUUID.of(uuid));
        if (game == null) {
            return null;
        }
        return GameMapper.toDTO(game);
    }
}