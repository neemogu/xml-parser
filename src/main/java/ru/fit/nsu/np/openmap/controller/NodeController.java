package ru.fit.nsu.np.openmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fit.nsu.np.openmap.bean.NodeBean;
import ru.fit.nsu.np.openmap.service.NodeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/node")
public class NodeController {

    private final NodeService nodeService;

    @PostMapping
    public NodeBean createNode(@Valid @RequestBody NodeBean bean) {
        return nodeService.create(bean);
    }

    @PutMapping
    public ResponseEntity<NodeBean> updateNode(@Valid @RequestBody NodeBean bean) {
         NodeBean updated = nodeService.update(bean);
         if (updated == null) {
             return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteNode(@PathVariable(name = "id") Long id) {
        nodeService.delete(id);
    }

    @GetMapping("/list")
    public List<NodeBean> getNodes(@PageableDefault Pageable pageable) {
        return nodeService.loadAll(pageable);
    }
}
