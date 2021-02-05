package com.tianruoxi.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianruoxi.server.mapper.OplogMapper;
import com.tianruoxi.server.pojo.Oplog;
import com.tianruoxi.server.service.IOplogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Tianruoxi
 * @since 2021-02-05
 */
@Service
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
