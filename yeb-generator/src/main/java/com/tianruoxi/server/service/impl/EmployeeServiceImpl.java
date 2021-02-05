package com.tianruoxi.server.service.impl;

import com.tianruoxi.server.pojo.Employee;
import com.tianruoxi.server.mapper.EmployeeMapper;
import com.tianruoxi.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
