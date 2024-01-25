package guru.maze.avatar.model.inter;

/**
 *
 * @author xiaok
 * @date 2018/10/17
 *
 */

public interface EventInterface<T,F> {
    void  onSuccess(T t);
    void onFail(F t);

}
