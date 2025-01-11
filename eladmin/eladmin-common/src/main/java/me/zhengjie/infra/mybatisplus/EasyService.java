package me.zhengjie.infra.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.utils.PageResult;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface EasyService<T> extends IService<T> {
    LambdaQueryWrapper<T> quick();

    <G> int quickSave(G resources);

    <G> int quickSaveBatch(List<G> resources);

    <G> int quickModifyById(G resources);

    <G> boolean quickModifyBatchById(Collection<G> resources, int batchSize);

    <G> G quickGetOneById(Serializable id, Class<G> targetClazz);

    T quickGetOne(LambdaQueryWrapper<T> wrapper, SFunction<T, ?> orderColumn);

    <G> G quickGetOne(LambdaQueryWrapper<T> wrapper, SFunction<T, ?> orderColumn, Class<G> clazz);

    <G> List<G> quickListByIds(List<Serializable> ids, Class<G> targetClazz);

    <Q> List<T> quickList(Q criteria);

    List<T> quickList(LambdaQueryWrapper<T> wrapper);

    <G, Q> List<G> quickList(Q criteria, Class<G> targetClazz);

    <G> List<G> quickList(LambdaQueryWrapper<T> wrapper, Class<G> targetClazz);

    PageResult<T> quickPage(LambdaQueryWrapper<T> wrapper, IPage<T> pageable);

    <G> PageResult<G> quickPage(LambdaQueryWrapper<T> wrapper, IPage<T> pageable, Class<G> targetClazz);

    <G, Q> PageResult<G> quickPage(Q criteria, IPage<T> pageable, Class<G> targetClazz);

    <Q> PageResult<T> quickPage(Q criteria, IPage<T> pageable);

    int quickUpdate(LambdaQueryWrapper<T> wrapper, T entity);

    int quickDelete(LambdaQueryWrapper<T> wrapper);
}
