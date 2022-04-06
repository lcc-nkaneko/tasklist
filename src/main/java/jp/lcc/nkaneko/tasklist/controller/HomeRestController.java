package jp.lcc.nkaneko.tasklist.controller;

import jp.lcc.nkaneko.tasklist.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
public class HomeRestController {

    record TaskItem(String id, String task, String deadline, boolean done) {}
    private List<TaskItem> taskItems = new ArrayList<>();
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/resthello")
    String hello() {
        return """
                Hello.
                It works!
                現在時刻は%sです。
                """.formatted(LocalDateTime.now());
    }

    //レスポンスはJSON形式で返す。
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    //ジェネリクスがObjectだとレスポンスはJSON形式で返す。
    @GetMapping("/response")
    public ResponseEntity<Object> object(@RequestParam(value = "str", defaultValue = "World") String str) {
        return new ResponseEntity<Object>(new Greeting(counter.incrementAndGet(), str), HttpStatus.CONFLICT);
    }

    //レスポンスはJSON形式で返す。第二引数にステータスコードを加えることが出来る。
    @GetMapping("/resGreeting")
    public ResponseEntity<Greeting> resGreeting(@RequestParam(value = "str", defaultValue = "resGreeting") String str) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), str);
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @GetMapping("/restadd")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline){
        String id = UUID.randomUUID().toString().substring(0,8);
        TaskItem item = new TaskItem(id, task, deadline,  false);
        taskItems.add(item);

        return "タスクを追加しました";
    }

    @GetMapping("/restlist")
    String listItems() {
        String result = taskItems.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(","));
        return result;
    }
}
