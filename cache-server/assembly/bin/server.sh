#!/bin/bash
#
#此脚本为Linux下启动java程序的通用脚本。（包含启动，停止，重启）
#cd 进入脚本执行的bin目录，sh server.sh start(启动) | stop（停止）| restart(重启)
#

# 使用命令
USAGE="Usage: startup.sh {start|stop|restart} {dev|pro|pre} {debug|jmx|null}"

# 参数个数
exec_param_count=$#
if [ $1 != "stop" -a $exec_param_count -lt 3 ]
then
   echo $USAGE
   exit
fi

#启动profile: dev/pro 默认pro
profile=$2
if [ $1 != "stop" ] && [ "$profile" != 'pro' ] && [ "$profile" != 'dev' ] && [ "$profile" != 'pre' ] && [ "$profile" != 'test' ]
then
    echo $USAGE
    echo "error param: $profile"
    exit
fi
JVM_MEM="512m"
if [ "$profile" != 'pro' ]
then
    JVM_MEM='512m'
fi

#启动模式：debug/jmx/null 默认null
mode=$3
if [ $1 != "stop" ] && [ "$mode" != 'debug' ] && [ "$mode" != 'jmx' ] && [ "$mode" != 'null' ]
then
    echo $USAGE
    echo "error param: $mode"
    exit
fi

#bin目前路径以及相关目录路径
cd `dirname $0`
BIN_DIR=`pwd`
DEPLOY_DIR=`pwd`
SERVER_NAME=$DEPLOY_DIR
CONF_DIR=$DEPLOY_DIR/conf
LIB_DIR=$DEPLOY_DIR
MAIN_CLASS=org.springframework.boot.loader.JarLauncher


start(){
    echo "CONF_DIR=$CONF_DIR"
	PIDS=`ps -ef | grep java | grep -v grep | grep "$CONF_DIR" |awk '{print $2}'`
	if [ -n "$PIDS" ]; then
	    echo "ERROR: The $SERVER_NAME already started!"
	    echo "PID: $PIDS"
	    exit 1
	fi

	LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

    # java opts
	JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true  -Dfile.encoding=UTF-8"
	JAVA_DEBUG_OPTS=""
	if [ "$mode" = "debug" ]; then
	    JAVA_DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=18000,server=y,suspend=n "
	fi

	# java jmx opts
	JAVA_JMX_OPTS=""
	if [ "$mode" = "jmx" ]; then
		ip=$(ifconfig | grep "inet addr" | grep -v 127.0.0.1 | awk '{print $2}' | awk -F ':' '{print $2}')
	    JAVA_JMX_OPTS="-Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.rmi.server.hostname=$ip "
	fi

	# java mem opts
	JAVA_MEM_OPTS=""
	BITS=`file $JAVA_HOME/bin/java | grep 64-bit`
	echo "BITS =$BITS"
	if [ -n "$BITS" ]; then
		  JAVA_MEM_OPTS="-server -Xmx$JVM_MEM -Xms$JVM_MEM -Xmn128m -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
	else
		JAVA_MEM_OPTS="-server -Xms1g -Xmx1g -XX:SurvivorRatio=2 -XX:+UseParallelGC "
	fi


	echo "Starting the $SERVER_NAME ..."
	echo ""
	echo "............"
	echo "nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$LIB_JARS $MAIN_CLASS --spring.profiles.active=$profile > /dev/null  2>&1 &"
	echo ""

	nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$LIB_JARS $MAIN_CLASS --spring.profiles.active=$profile > /dev/null  2>&1 &

	COUNT=0
	while [ $COUNT -lt 1 ]; do
	    echo "......"
	    sleep 1
	    COUNT=`ps -ef | grep java | grep -v grep | grep "$CONF_DIR" | awk '{print $2}' | wc -l`
			if [ $COUNT -gt 0 ]; then
				break
			fi
	done
	echo "............"
	echo "The $SERVER_NAME start OK!"
	PIDS=`ps -ef | grep java | grep -v grep | grep "$CONF_DIR" | awk '{print $2}'`
	echo "PID: $PIDS"

}

stop(){
		PIDS=`ps -ef | grep java | grep -v grep | grep "$CONF_DIR" | awk '{print $2}'`
		if [ -z "$PIDS" ]; then
		    echo "ERROR: The $SERVER_NAME does not started!"
		    exit 1
		fi

		echo "Stopping the $SERVER_NAME ..."
		echo "............"
		for PID in $PIDS ; do
			kill $PID > /dev/null 2>&1
		done

		COUNT=0
		while [ $COUNT -lt 1 ]; do
		    echo "..."
		    sleep 1
		    COUNT=1
		    for PID in $PIDS ; do
				PID_EXIST=`ps -f -p $PID | grep java`
				if [ -n "$PID_EXIST" ]; then
					COUNT=0
					break
				fi
			done
		done
		echo ""
		echo "$SERVER_NAME stop OK!"
		echo "PID: $PIDS"

}


case $1 in
  start)
		start;
    ;;
  stop)
		stop;
    ;;
  restart)
		echo "############ Application of '"$MAIN_CLASS"' restarting....############"
    stop;
    sleep 1
    start;
    ;;
  *)
    echo $USAGE
    ;;
esac
exit 0