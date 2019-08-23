package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.common.enums.AdminStatus;
import cn.abovesky.shopping.common.enums.UserStatus;
import cn.abovesky.shopping.dao.AdminMapper;
import cn.abovesky.shopping.domain.Admin;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IAdminService;
import cn.abovesky.shopping.util.IdStatusSplitUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by snow on 2014/5/11.
 */
@Transactional(rollbackFor = Exception.class)
@Service(IAdminService.SERVICE_NAME)
public class AdminSerivceImpl implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }

    @Override
    public List<Admin> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Admin> adminList = adminMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(adminMapper.getTotalCount(vo));
        return adminList;
    }

    @Override
    public Admin findById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Admin admin) throws ServiceException {
        admin.setUpdateDate(new Date());
        if (adminMapper.updateByPrimaryKeySelective(admin) < 1) {
            throw new ServiceException("修改失败");
        }
    }

    @Override
    public void active(String[] ids) throws ServiceException {
        if (IdStatusSplitUtils.isFormatSecret(ids, AdminStatus.PENDING.toString(), AdminStatus.INACTIVE.toString())) {
            adminMapper.active(IdStatusSplitUtils.split2Id(ids));
        } else {
            throw new ServiceException("只能操作审核中和被禁用的用户");
        }
    }

    @Override
    public void inactive(String[] ids) throws ServiceException {
        if (IdStatusSplitUtils.isFormatSecret(ids, AdminStatus.PENDING.toString(), AdminStatus.ACTIVE.toString())) {
            adminMapper.inactive(IdStatusSplitUtils.split2Id(ids));
        } else {
            throw new ServiceException("只能操作审核中和正常使用的用户");
        }
    }

    @Override
    public void resetPwd(String[] ids) {
        for (Integer id : IdStatusSplitUtils.split2Id(ids)) {
            Admin admin = new Admin();
            admin.setId(id);
            admin.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            adminMapper.updateByPrimaryKeySelective(admin);
        }
    }

    @Override
    public void add(Admin admin) throws ServiceException {
        Date now = new Date();
        admin.setInsertDate(now);
        admin.setUpdateDate(now);
        admin.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        admin.setStatus(UserStatus.ACTIVE.toString());
        if (adminMapper.insertSelective(admin) < 1) {
            throw new ServiceException("添加失败");
        }
    }

    @Override
    public String checkUsername(String username) {
        if (adminMapper.findByUsername(username) != null) {
            return "false";
        } else {
            return "true";
        }
    }

    @Override
    public void changePwd(Integer id, String oldPassword, String newPassword) throws ServiceException {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if (admin != null && admin.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))) {
            admin.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            if (adminMapper.updateByPrimaryKeySelective(admin) < 1) {
                throw new ServiceException("修改失败");
            }
        } else {
            throw new ServiceException("原密码错误");
        }
    }
}
