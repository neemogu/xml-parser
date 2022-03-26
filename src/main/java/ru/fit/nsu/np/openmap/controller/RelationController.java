package ru.fit.nsu.np.openmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fit.nsu.np.openmap.bean.RelationBean;
import ru.fit.nsu.np.openmap.service.RelationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relation")
public class RelationController {

    private final RelationService relationService;

    @PostMapping
    public RelationBean createNode(@Valid @RequestBody RelationBean bean) {
        return relationService.create(bean);
    }

    @PutMapping
    public ResponseEntity<RelationBean> updateNode(@Valid @RequestBody RelationBean bean) {
        RelationBean updated = relationService.update(bean);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteNode(@PathVariable(name = "id") Long id) {
        relationService.delete(id);
    }

    @GetMapping("/list")
    public List<RelationBean> getNodes(@PageableDefault Pageable pageable) {
        return relationService.loadAll(pageable);
    }

}
