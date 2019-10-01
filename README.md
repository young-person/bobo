# bobo

#### 介绍
bobo工程

#### 软件架构
软件架构说明


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技
 <build>
    <finalName>cooperate-case-server</finalName>
    <resources>
        <resource>
            <!--配置文件路径 -->
            <directory>src/main/resources</directory>
        </resource>
        <resource>
            <!--配置文件路径 -->
            <directory>config</directory>
            <!--开启filtering功能 -->
            <filtering>true</filtering>
        </resource>
    </resources>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <configuration>
                <skip>true</skip>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <configuration>
                <encoding>utf-8</encoding>
                <useDefaultDelimiters>true</useDefaultDelimiters>
            </configuration>
        </plugin>
    </plugins>
    </build>

 MERGE INTO
	XT_FLOW_LIST P
		USING(
		SELECT
			? AS workId
		FROM
			DUAL
	) D ON
	(
		P.WORK_ID = D.workId
	)
	WHEN MATCHED THEN UPDATE
	SET
		POLICE_CASE_NO =?,
		POLICE_CASE_NAME =?,
		SUSPECTS =?,
		FLOW_ID =?,
		FLOW_CODE =?,
		FLOW_NAME =?,
		STARTER =?,
		STARTER_NAME =?,
		STARTER_ROLES =?,
		STARTER_ORG =?,
		CURRENT_NODE_ID =?,
		CURRENT_NODE_CODE =?,
		CURRENT_NODE_NAME =?,
		CURRENT_ORG_CODE =?,
		CURRENT_POLITIC_CODE =?,
		CURRENT_ORG_NAME =?,
		CURRENT_ROLES =?,
		CURRENT_ROLES_NAME =?,
		CURRENT_ROLES_NUM =?,
		EX_NODE_ID =?,
		EX_NODE_CODE =?,
		EX_NODE_NAME =?,
		EX_HANDLE_TIME =?,
		EX_ORG_CODE =?,
		EX_ORG_NAME =?,
		START_TIME =?,
		FLOW_STATUS =?,
		UPDATE_TIME =?
		WHEN NOT MATCHED THEN INSERT
			(
				ID,
				POLICE_CASE_NO,
				POLICE_CASE_NAME,
				CASE_CAUSE,
				CASE_CAUSE_NAME,
				SUSPECTS,
				WORK_ID,
				FLOW_ID,
				FLOW_CODE,
				FLOW_NAME,
				STARTER,
				STARTER_NAME,
				STARTER_ROLES,
				STARTER_POLITIC_CODE,
				STARTER_ORG,
				CURRENT_NODE_ID,
				CURRENT_NODE_CODE,
				CURRENT_NODE_NAME,
				CURRENT_ORG_CODE,
				CURRENT_POLITIC_CODE,
				CURRENT_ORG_NAME,
				CURRENT_ROLES,
				CURRENT_ROLES_NAME,
				CURRENT_ROLES_NUM,
				EX_NODE_ID,
				EX_NODE_CODE,
				EX_NODE_NAME,
				EX_HANDLE_TIME,
				EX_ORG_CODE,
				EX_ORG_NAME,
				START_TIME,
				FLOW_STATUS,
				CURRENT_RECEIVE_STATUS,
				CREATE_TIME,
				UPDATE_TIME
			)
		VALUES(
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?,
			?
		)s