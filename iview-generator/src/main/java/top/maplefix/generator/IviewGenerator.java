package top.maplefix.generator;


import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import top.maplefix.generator.util.GenCodeUtil;

/**
 * @author Maple
 * @description 快速生成iview-core前后端代码的插件
 * @date 2021/1/27 10:05
 */
@Mojo(name = "iviewGenerator", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class IviewGenerator extends AbstractMojo {

    /**
     * @param 数据库地址
     */
    @Parameter(property = "jdbcUrl", defaultValue = "jdbc:mysql://127.0.0.1:3306/boot-iview?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai")
    private String jdbcUrl;

    /**
     * @param 数据库账号
     */
    @Parameter(property = "userName", defaultValue = "root")
    private String userName;

    /**
     * @param 数据库密码
     */
    @Parameter(property = "password", defaultValue = "123456")
    private String password;

    /**
     * @param 数据库驱动名称
     */
    @Parameter(property = "driverClassName", defaultValue = "com.mysql.jdbc.Driver")
    private String driverClassName;

    /**
     * @param web后端工程所在的位置
     */
    @Parameter(property = "webCorePath", defaultValue = "top.maplefix.iviewcore")
    private String webCorePath;

    /**
     * @param web后端mybatsi的路径
     */
    @Parameter(property = "mybatisBasePath", defaultValue = "/resources/mybatis/mapper")
    private String mybatisBasePath;


    /**
     * @param 开发作者
     */
    @Parameter(property = "author", defaultValue = "maplefix")
    private String author;

    /**
     * @param 表名
     */
    @Parameter(property = "tableName")
    private String tableName;

    /**
     * @param 实体名称
     */
    @Parameter(property = "beanName")
    private String beanName;


    /**
     * @param web前端工程所在的位置
     */
    @Parameter(property = "webPath")
    private String webPath;

    /**
     * @param 当前创建的节点需要挂载到那个节点底下
     */
    @Parameter(property = "routerNode", defaultValue = "sys")
    private String routerNode;

    /**
     * @param 生成的sql脚本的放置的位置
     */
    @Parameter(property = "sqlPath", defaultValue = "")
    private String sqlPath;


    @Override
    public void execute() throws MojoFailureException {
        if (StringUtils.isEmpty(tableName)) {
            throw new MojoFailureException("表名不能为空！");
        }
        if (StringUtils.isEmpty(beanName)) {
            throw new MojoFailureException("实体名称不能为空！");
        }
        try {
            // 生成后端代码
            GenCodeUtil.genFiles(
                    author, tableName,
                    webCorePath, mybatisBasePath,
                    beanName, driverClassName,
                    userName, password, jdbcUrl);
            // 生成前端代码
            GenCodeUtil.genWebFiles(
                    author, tableName, webPath,
                    beanName, driverClassName,
                    userName, password, jdbcUrl,
                    routerNode, sqlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
