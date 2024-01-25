package guru.maze.avatar.model.bean.digital;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author by xiaok
 * @date 2023/10/27
 */
public  abstract class DigitalSectionEntity implements SectionEntity  {

    @Override
    public int getItemType() {
        if (isHeader()) {
            return SectionEntity.Companion.HEADER_TYPE;
        } else {
            // 拷贝 重写此处，返回自己的多布局类型
            return getDigitalType();
        }
    }

    public abstract int getDigitalType();

}
