package cn.abovesky.shopping.service;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.domain.Merchant;
import cn.abovesky.shopping.exception.ServiceException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by snow on 2014/4/20.
 */
public interface IMerchantService {
    static final String SERVICE_NAME = "merchantService";
    boolean isUniqueUsername(String username);
    void insertMerchant(Merchant merchant) throws ServiceException;
    void update(MultipartFile image, Merchant merchant) throws ServiceException;
    Merchant findByUsername(String username);
    List<Merchant> findPageBreakByCondition(BaseConditionVO vo);
    Integer getCollectionCountById(Integer id);

    void changePwd(Integer id, String oldPassword, String newPassword) throws ServiceException;

    Merchant findById(Integer id);

    String getImageById(Integer id);

    List<Merchant> findAll();

    void active(String[] ids) throws ServiceException;

    void inactive(String[] ids) throws ServiceException;

    void resetPwd(String[] ids);

    String checkStoreName(String storeName);
}
