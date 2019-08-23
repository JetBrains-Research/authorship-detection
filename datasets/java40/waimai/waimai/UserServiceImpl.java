package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.common.enums.UserStatus;
import cn.abovesky.shopping.dao.UserMapper;
import cn.abovesky.shopping.domain.User;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IUserService;
import cn.abovesky.shopping.util.IdStatusSplitUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * Created by snow on 2014/4/26.
 */
@Transactional(rollbackFor = Exception.class)
@Service(IUserService.SERVICE_NAME)
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(User user) throws ServiceException {
        if (this.countByUsername(user.getUsername()) < 1) {
            user.setStatus(UserStatus.ACTIVE.toString());
            user.setCredit(0);
            userMapper.insert(user);
        } else {
            throw new ServiceException("用户名已存在");
        }
    }

    @Override
    public Integer countByUsername(String username) {
        return userMapper.countByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void editPwd(User user, String newPwd) throws ServiceException {
        User originalUser = userMapper.selectByPrimaryKey(user.getId());
        if (originalUser != null && originalUser.getPassword().equals(user.getPassword())) {
            user.setPassword(newPwd);
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            throw new ServiceException("原密码错误或用户不存在");
        }
    }

    @Override
    public Integer getCreditByUserId(Integer userId) {
        return userMapper.getCreditByUserId(userId);
    }

    @Override
    public User findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<User> userList = userMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(userMapper.getTotalCount(vo));
        return userList;
    }

    @Override
    public void active(String[] ids) throws ServiceException {
        if (IdStatusSplitUtils.isFormatSecret(ids, UserStatus.PENDING.toString(), UserStatus.INACTIVE.toString())) {
            userMapper.active(IdStatusSplitUtils.split2Id(ids));
        } else {
            throw new ServiceException("只能操作审核中和被禁用的用户");
        }
    }

    @Override
    public void inactive(String[] ids) throws ServiceException {
        if (IdStatusSplitUtils.isFormatSecret(ids, UserStatus.PENDING.toString(), UserStatus.ACTIVE.toString())) {
            userMapper.inactive(IdStatusSplitUtils.split2Id(ids));
        } else {
            throw new ServiceException("只能操作审核中和正常使用的用户");
        }
    }

    @Override
    public void resetPwd(String[] ids) {
        for (Integer id : IdStatusSplitUtils.split2Id(ids)) {
            User user = new User();
            user.setId(id);
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            userMapper.updateByPrimaryKeySelective(user);
        }
    }
}
