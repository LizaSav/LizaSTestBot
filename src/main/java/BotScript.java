import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class BotScript {
    private Map<Integer, ScriptRow> script;

    public String getContentById(Integer id) {
        return script.get(id).getContent();
    }

    public List<ScriptRow> getOptionsById(Integer id) {
        return script.get(id).getOptionIds()
                .stream()
                .map(optionId -> script.get(optionId))
                .collect(Collectors.toList());
    }

}
