/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greendao;

import java.io.FileInputStream;
import java.util.Properties;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the example project DaoExample. Run it as a
 * Java application (not Android).
 *
 * @author Markus
 */
public class ExampleDaoGenerator
{

    /**
     * 数据库版本号
     */
    private static final int DB_VERSION = 61;

    public static void main(String[] args) throws Exception
    {

        // 加载本地配置文件中保存的路径
        String savePath = "D:\\db";
        if(null == savePath || savePath.length() == 0)
        {
            throw new RuntimeException("请在配置文件中输入 本地GreenDao输出目录");
        }
        System.out.println("ProjectRootPath  :  " + savePath);

        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        // 当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
        Schema schema = new Schema(DB_VERSION, "com.test.entity");
        schema.setDefaultJavaPackageDao("com.test.db.greendao.dao");
        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep
        // sections。即：重新生成之后keep之间的代码还会保留
        // schema.enableActiveEntitiesByDefault();
        schema.enableKeepSectionsByDefault();

        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
        // addTaskWorkLoadDocTable(schema);
        addTaskViewLoadDocTable(schema);
        // 最后我们将使用 DAOGenerator 类的 generateAll()
        // 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema, savePath);
    }

    /**
     * @param schema
     */
    private static void addTaskViewLoadDocTable(Schema schema)
    {
        Entity entity = schema.addEntity("TaskViewEntity");
        entity.setJavaPackage("com.test.entity.task");
        entity.setJavaPackageDao("com.test.db.greendao.dao.task");
        entity.setJavaDoc("任务视图");
        entity.implementsSerializable();
        entity.addStringProperty("guid").primaryKey().javaDocField("主键ID");
        entity.addStringProperty("taskId").javaDocField("任务ID");
        entity.addStringProperty("workDocId").javaDocField("产出文档ID");
        entity.addStringProperty("docSubject").javaDocField("产出文档标题");
        entity.addStringProperty("fileId").javaDocField("关联文档表的docId");
        entity.addStringProperty("createUser").javaDocField("创建用户");
        entity.addStringProperty("createDate").javaDocField("创建时间");
        entity.addStringProperty("updateDate").javaDocField("更新时间");
    }

}
