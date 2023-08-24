package xyz.blushyes.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.util.Assert;
import xyz.blushyes.common.PageRequest;
import xyz.blushyes.common.PageVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 分页查询工具类
 */
public class PageUtils {
    private static final int DEFAULT_SIZE = 10;

    private PageUtils() {
    }

    /**
     * 分页查询
     * 注意：方法内部不能对查询结果做增删处理
     *
     * @param pageNo        页码
     * @param pageSize      页大小
     * @param count         是否count
     * @param querySupplier 查询方法
     * @param <E>           结果类型
     * @return
     */
    public static <E> PageVO<E> execute(int pageNo, int pageSize, boolean count, Supplier<List<E>> querySupplier) {
        Page<E> pagingResult = PageHelper.startPage(pageNo, pageSize, count)
                .doSelectPage(querySupplier::get);
        Assert.notNull(pagingResult.getResult(), "列表查询结果返回null!");

        PageVO<E> PageVO = new PageVO<>();
        PageVO.setPageNo(pagingResult.getPageNum());
        PageVO.setPageSize(pagingResult.getPageSize());
        PageVO.setTotal(Math.toIntExact(pagingResult.getTotal()));
        PageVO.setRows(pagingResult.getResult());
        return PageVO;
    }

    /**
     * 分页查询, 默认不count
     * 注意：方法内部不能对查询结果做增删处理
     *
     * @param pageNo        页码
     * @param pageSize      页大小
     * @param querySupplier 查询方法
     * @param <E>           结果类型
     * @return
     */
    public static <E> PageVO<E> execute(int pageNo, int pageSize, Supplier<List<E>> querySupplier) {
        return execute(pageNo, pageSize, false, querySupplier);
    }

    /**
     * 分页查询, 默认不count, 默认分页大小是10
     * 注意：方法内部不能对查询结果做增删处理
     *
     * @param pageNo        页码
     * @param querySupplier 查询方法
     * @param <E>           结果类型
     * @return
     */
    public static <E> PageVO<E> execute(int pageNo, Supplier<List<E>> querySupplier) {
        return execute(pageNo, DEFAULT_SIZE, querySupplier);
    }

    /**
     * 分页查询
     * 注意：方法内部不能对查询结果做增删处理
     *
     * @param request       查询请求
     * @param querySupplier 查询方法
     * @param <R>           查询条件
     * @param <E>           结果类型
     * @return
     */
    public static <R extends PageRequest, E> PageVO<E> execute(R request,
                                                               Supplier<List<E>> querySupplier) {
        Objects.requireNonNull(request);
        return execute(request.getPageNo(), request.getPageSize(), request.getCount(), querySupplier);
    }

    /**
     * 分页查询并转换查询结果
     * 注意：方法内部不能对查询结果做增删处理
     *
     * @param request         查询请求, 包含了分页信息
     * @param querySupplier   查询方法, 使用 ()-> mapper.query();
     * @param convertFunction 查询结果转换返回结果方式, 使用 po->{Vo vo=new VO();...; return vo;}
     * @param <P>             查询结果类型, 一般是PO
     * @param <V>             返回结果类型, 一般是VO
     * @return
     */
    public static <R extends PageRequest, P, V> PageVO<V> executeConvert(R request,
                                                                         Supplier<List<P>> querySupplier,
                                                                         Function<P, V> convertFunction) {
        PageVO<P> originalPageVO = execute(request, querySupplier);

        List<V> listResult = new ArrayList<>(originalPageVO.getRows().size());
        originalPageVO.getRows().forEach(item -> listResult.add(convertFunction.apply(item)));

        PageVO<V> PageVO = new PageVO<>();
        PageVO.setPageNo(originalPageVO.getPageNo());
        PageVO.setPageSize(originalPageVO.getPageSize());
        PageVO.setTotal(originalPageVO.getTotal());
        PageVO.setRows(listResult);
        return PageVO;
    }

    /**
     * 分页查询并转换查询结果列表
     * 注意：方法内部不能对查询结果做增删处理
     *
     * @param request         查询请求, 包含了分页信息
     * @param querySupplier   查询方法, 使用 ()-> mapper.query();
     * @param convertFunction 查询结果列表转换返回结果列表方式, 使用 poList->{List voList=new List();...; return voList;}
     * @param <P>             查询结果类型, 一般是PO
     * @param <V>             返回结果类型, 一般是VO
     * @return
     */
    public static <R extends PageRequest, P, V> PageVO<V> executeConvertList(R request,
                                                                             Supplier<List<P>> querySupplier,
                                                                             Function<List<P>, List<V>> convertFunction) {
        PageVO<P> originalPageVO = execute(request, querySupplier);

        List<V> listResult = convertFunction.apply(originalPageVO.getRows());

        PageVO<V> PageVO = new PageVO<>();
        PageVO.setPageNo(originalPageVO.getPageNo());
        PageVO.setPageSize(originalPageVO.getPageSize());
        PageVO.setTotal(originalPageVO.getTotal());
        PageVO.setRows(listResult);

        Assert.isTrue(originalPageVO.getRows().size() == listResult.size(), "列表长度转换前后结果不一致!");

        return PageVO;
    }
}