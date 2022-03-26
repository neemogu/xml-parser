package ru.fit.nsu.np.openmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fit.nsu.np.openmap.bean.WayBean;
import ru.fit.nsu.np.openmap.service.WayService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/way")
public class WayController {

    private final WayService wayService;

    @PostMapping
    public WayBean createNode(@Valid @RequestBody WayBean bean) {
        return wayService.create(bean);
    }

    @PutMapping
    public ResponseEntity<WayBean> updateNode(@Valid @RequestBody WayBean bean) {
        WayBean updated = wayService.update(bean);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteNode(@PathVariable(name = "id") Long id) {
        wayService.delete(id);
    }

    @GetMapping("/list")
    public List<WayBean> getNodes(@PageableDefault Pageable pageable) {
        return wayService.loadAll(pageable);
    }
}
