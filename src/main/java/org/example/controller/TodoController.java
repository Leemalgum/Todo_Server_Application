package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin //CORS 이슈를 방어하기 위한 어노테이션
@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoService service;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        System.out.println("CREATE");

        if (ObjectUtils.isEmpty(request.getTitle())) //title의 값이 없다 = 비정상적인 동작
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getOrder())) //order와 completed의 값이 없다 = 기본값 설정해주기
            request.setOrder(0L);

        if (ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);

        TodoEntity result = this.service.add(request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping("{id}") //경로에 id값을 받음
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) { //경로로 받은 id 값을 사용하기 위한 어노테이션
        System.out.println("READ ONE");
        TodoEntity result = this.service.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        System.out.println("READ ALL");
        List<TodoEntity> list = this.service.searchAll();
        List<TodoResponse> responses = list.stream().map(TodoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        System.out.println("UPDATE");
        TodoEntity result = this.service.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        System.out.println("DELETE");
        this.service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        System.out.println("DELETE ALL");
        this.service.deleteAll();
        return ResponseEntity.ok().build();
    }


}
