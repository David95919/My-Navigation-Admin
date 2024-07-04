package one.moonx.navigation.controller.admin;

import one.moonx.navigation.base.Result;
import one.moonx.navigation.constant.MessageConstant;
import one.moonx.navigation.convert.TagConvert;
import one.moonx.navigation.pojo.dto.TagDTO;
import one.moonx.navigation.pojo.vo.TagVO;
import one.moonx.navigation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagConvert tagConvert;

    /**
     * 获取标签
     *
     * @return {@link Result }<{@link List }<{@link TagVO }>>
     */
    @GetMapping
    public Result<List<TagVO>> getTag() {
        List<TagVO> tagVOS = tagConvert.convertVO(tagService.list());
        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, tagVOS);
    }

    /**
     * 按 ID 获取标签
     *
     * @param id 同上
     * @return {@link Result }<{@link TagVO }>
     */
    @GetMapping("/{id}")
    public Result<TagVO> getTagById(@PathVariable Integer id) {
        TagVO tagVO = tagConvert.convertVO(tagService.getById(id));
        return Result.success.msgAndData(MessageConstant.GET_SUCCESS, tagVO);
    }

    /**
     * 创建标签
     *
     * @param tagDTO 标记 DTO
     * @return {@link Result }<{@link String }>
     */
    @PostMapping
    public Result<String> createTag(@RequestBody TagDTO tagDTO) {
        tagService.createTag(tagDTO);
        return Result.success.msg(MessageConstant.ADD_SUCCESS);
    }

    /**
     * 更新标签
     *
     * @param tagDTO 标签 DTO
     * @return {@link Result }<{@link String }>
     */
    @PutMapping
    public Result<String> updateTag(@RequestBody TagDTO tagDTO) {
        tagService.updateTag(tagDTO);
        return Result.success.msg(MessageConstant.UPDATE_SUCCESS);
    }

    /**
     * 删除标签
     *
     * @param id id
     * @return {@link Result }<{@link String }>
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteTag(@PathVariable int id) {
        tagService.removeById(id);
        return Result.success.msg(MessageConstant.DELETE_SUCCESS);
    }
}
