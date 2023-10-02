package no.hvl.dat250.rest.todos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest-Endpoint for todos.
 */

@RestController
@RequestMapping("/todos")
public class TodoController {

    public static final String TODO_WITH_THE_ID_X_NOT_FOUND = "Todo with the id %s not found!";

    private Map<Long, Todo> todoMap = new HashMap<>();
    private long currentId = 0;

    @PostMapping
    public ResponseEntity<Todo> post(@RequestBody Todo todo) {
        todo.setId(currentId);
        todoMap.put(currentId,todo);
        currentId++;
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAll() {
        List<Todo> todoList = new ArrayList<>(todoMap.values());
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Todo todo = todoMap.get(id);

        if (todo == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Todo todo) {
        if(!todoMap.containsKey(id)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        todo.setId(id);
        todoMap.put(id,todo);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(!todoMap.containsKey(id)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        todoMap.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
