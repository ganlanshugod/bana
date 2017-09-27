1. ## common-util的部署说明
使用插件release插件进行部署，首先进行prepare检查，部署之前要确定当前项目中的文档和内容都已经提交到了版本库，执行命令如下:
> mvn release:prepare

如果正常执行下来之后，可以使用 命令提交，会将归档版本并切换为新的版本。命令如下
> mvn release:perform

如果执行过程中希望跳过测试代码，可以增加这个参数如下：
> mvn release:prepare -Darguments="-DskipTests"


