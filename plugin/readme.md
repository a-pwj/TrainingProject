1、创建插件步骤
第一步：新建一个Android工程

第二步：在该工程中新建一个Module项目，类型选择Java Library

第三步：将Module里的内容删除，只保留build.gradle文件和src/main目录，同时移除build.gradle文件里的内容

第四步：建立Gradle插件目录(不一定需要创建groovy 可以使用java)

由于gradle是基于groovy，因此，我们开发的gradle插件相当于一个groovy项目。
所以需要在main目录下新建groovy目录，这时候groovy文件夹会被Android识别为groovy源码目录。
除了在main目录下新建groovy目录外，你还要在main目录下新建resources目录，同理resources目录会被自动识别为资源文件夹。
在groovy目录下新建项目包名，就像Java包名那样。resources目录下新建文件夹META-INF，META-INF文件夹下新建gradle-plugins文件夹。
这样，就完成了gradle 插件的项目的整体搭建。目前项目的结构是这样的：


第五步：修改build.gradle文件

内容如下：

apply plugin: 'groovy'
apply plugin: 'maven'

dependencies{
	// gradle sdk
    compile gradleApi()
    // groovy sdk
    compile localGroovy()
    compile 'com.android.tools.build:gradle:1.5.0'
}

repositories{
    mavenCentral()
}

第六步：在com.davisplugins包名下通过new -> file ->创建PluginImpl.groovy文件

内容如下：

package com.davisplugins

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class PluginImpl implements Plugin<Project>{

    void apply(Project project){
        System.out.println("========================");
        System.out.println("hello gradle plugin!");
        System.out.println("========================");
    }
}

第七步：定义插件名称

在resources/META-INF/gradle-plugins目录下新建一个properties文件，
注意该文件的命名就是你使用插件的名字，这里命名为davis.properties，
那么你在其他build.gradle文件中使用自定义的插件时候则需写成：

apply plugin: 'davis'

davis.properties文件内容：

implementation-class=com.davisplugins.PluginImpl

注意包名需要替换为你自己的包名。

现在你的目录结构如下：
