package com.tzplatform.service.ldap;

import com.tzplatform.entity.mobileplatform.MobileUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import java.util.ArrayList;
import java.util.List;

/**
 * 修改用户属性ldap工具类
 *
 * @author leijie
 */
@Component
public class LdapHelper {

    @Resource
    private LdapTemplate ldapTemplate;

    private static final String USER_PASSWORD = "userPassword";


    public void modifyUserPwd(MobileUser mobileUser, String passWord) {
        ldapTemplate.modifyAttributes(bulidUserDN(mobileUser), buildUserModifyAttributes(passWord));
    }

    public boolean checkLdapPassWord(MobileUser mobileUser, String password) {
        EqualsFilter f = new EqualsFilter("cn", mobileUser.getAccountId());
        return ldapTemplate.authenticate(bulidUserDN(mobileUser), f.toString(), password);
    }


    private DistinguishedName bulidUserDN(MobileUser mobileUser) {
        DistinguishedName dn = new DistinguishedName();
        dn.add("ou", "people");
        // 组织机构  学校编码
        if (mobileUser.getSchoolcode() != null) {
            dn.add("ou", mobileUser.getSchoolcode());
        }
        String userType = mobileUser.getUsertype();
        // 如果是学生
        if ("1".equals(userType)) {
            dn.add("ou", "student");
        } else if ("2".equals(userType)) { // 如果是老师
            dn.add("ou", "teacher");
        } else { // 外聘人员
            dn.add("ou", "wpry");
        }
        dn.add("cn", mobileUser.getAccountId());
        return dn;
    }

    public static ModificationItem[] buildUserModifyAttributes(String passWord) {

        List<ModificationItem> returnModificationItem = new ArrayList();
        if (StringUtils.isNotBlank(passWord)) {
            returnModificationItem.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(USER_PASSWORD, passWord)));
        }

        ModificationItem[] modificationItem = new ModificationItem[returnModificationItem.size()];
        for (int i = 0; i < returnModificationItem.size(); i++) {
            modificationItem[i] = returnModificationItem.get(i);
        }
        return modificationItem;
    }
}
