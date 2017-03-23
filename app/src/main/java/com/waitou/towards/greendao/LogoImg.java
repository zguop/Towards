package com.waitou.towards.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by waitou on 17/3/21.
 */

@Entity
public class LogoImg {

    @Id(autoincrement = true) //标识主键
    private Long id;

    @NotNull
    private String imgUrl;

    private String savePath;

    private boolean isShowLogoUrl;

    @Generated(hash = 1431844611)
    public LogoImg(Long id, @NotNull String imgUrl, String savePath,
            boolean isShowLogoUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.savePath = savePath;
        this.isShowLogoUrl = isShowLogoUrl;
    }

    @Generated(hash = 1551489630)
    public LogoImg() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public boolean getIsShowLogoUrl() {
        return this.isShowLogoUrl;
    }

    public void setIsShowLogoUrl(boolean isShowLogoUrl) {
        this.isShowLogoUrl = isShowLogoUrl;
    }


}

/*.
实体@Entity注解
schema：告知GreenDao当前实体属于哪个schema
active：标记一个实体处于活动状态，活动实体有更新、删除和刷新方法
nameInDb：在数据中使用的别名，默认使用的是实体的类名
indexes：定义索引，可以跨越多个列
createInDb：标记创建数据库表
*/

/*
2.基础属性注解
@Id :主键 Long型，可以通过@Id(autoincrement = true)设置自增长
@Property：设置一个非默认关系映射所对应的列名，默认是的使用字段名 举例：@Property (nameInDb="name")
@NotNul：设置数据库表当前列不能为空
@Transient ：添加次标记之后不会生成数据库表的列
*/
