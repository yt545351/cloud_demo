package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.Proclamation;
import com.example.system.vo.QueryProclamationVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yt
 * @since 2022-05-20
 */
public interface ProclamationService extends IService<Proclamation> {

    Object insertProclamation(Proclamation proclamation);

    Object getProclamationList(QueryProclamationVO queryVO);

    Object updateProclamation(Proclamation proclamation);

    Object getOne();

    Object deleteProclamation(Proclamation proclamation);
}
