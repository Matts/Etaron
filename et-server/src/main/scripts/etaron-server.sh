#!/bin/bash

RET_CODE_OK=0
RET_CODE_ERR=1
RET_CODE_FAILURE=2

#path to the script itself
APP_BINPATH=`dirname $0`

if [ ! -f ${APP_BINPATH}/setenv ] ;then
  echo "The setenv file could not be found in [${APP_BINPATH}] directory!"
  exit $RET_CODE_ERR
else
#setup environment variables
  . ${APP_BINPATH}/setenv
fi

#verifies if the current user is the one allowed to run the script
check_running_user()
{
  usr=`id|cut -d" " -f1|grep ${USER_RUN}`
  if [ -e $usr ]; then
     echo "[${APP_NAME}] can only be administered by the [${USER_RUN}] user!"
     exit $RET_CODE_ERR
  fi
}

#starts the tool
startme()
{
  check_running_user;

  # Check that the pid matches existing live process
  if [ -f ${APP_PID_FILE} ] ;then
    cupid=`cat $APP_PID_FILE`
    if ps -p $cupid>/dev/null ;then
      echo "[$APP_NAME] process [pid=$cupid] already running!"
      exit $RET_CODE_ERR
    fi
  fi

  APP_N=`basename $0`
  if [ -f ${APP_BIN_DIR}/${APP_N}.maintenance ]; then
     echo "[${APP_NAME}] is in maintenance mode and can NOT be started!"
     exit $RET_CODE_ERR
  fi

  echo "Launching application"

  # Change directory to application home
  cd ${APP_HOME}

  nohup java ${JVMPROP} ${APP_ENV} -DAPP_LOG_DIR=${APP_LOG_DIR} -cp ${APP_CLASSPATH} ${APP_MAINCLASS} $1 > ${APP_LOG_DIR}/info.stdout 2>&1 &
  # Save process id for shutdown
  cupid=$!
  echo $cupid > $APP_PID_FILE

  echo -n "Waiting [${WAITTOSTART}] seconds for the application to start "
  for i in `seq 1 ${WAITTOSTART}`;
  do
     echo -n "."
     sleep 1
  done

 echo ""

  #check that application started
  if [ -f ${APP_PID_FILE} ] ;then
    cupid=`cat ${APP_PID_FILE}`
    if ps -p ${cupid}>/dev/null ;then
      echo "[${APP_NAME}] process [pid=${cupid}] successfully started."
    else
      echo "[${APP_NAME}] process [pid=${cupid}] NOT started, please check log file(s)!"
      echo "Removing pidfile [${APP_PID_FILE}]"
      rm ${APP_PID_FILE}
      exit $RET_CODE_ERR
    fi
  fi
}

#stops the tool if running
stopme()
{
  check_running_user;

  if [ -f ${APP_PID_FILE} ] ; then
    cupid=`cat ${APP_PID_FILE}`
    if ps -p ${cupid}>/dev/null ; then
      echo "Trying to gracefully stop [${APP_NAME}] process [pid=$cupid]"
      kill -TERM ${cupid} >/dev/null 2>&1

      echo -n "Checking every [${WAITTODIE}] seconds if the application has stopped "
      for w in `seq 1 10` ; do
        if ! ps -p ${cupid}>/dev/null ; then
          echo -n "."
          break;
        fi
        sleep ${WAITTODIE}
      done

      echo ""

      if ps -p ${cupid}>/dev/null ; then
        echo "Application is still running! Forcefully terminating [${APP_NAME}] process [pid=${cupid}]"
        kill -KILL $cupid >/dev/null 2>&1
      fi
    fi

    echo "Application execution ended, removing pidfile [${APP_PID_FILE}]"
    rm -f $APP_PID_FILE
  else
    echo "Pid file for [${APP_NAME}] not found, assuming application is not running!"
  fi
}

#displays if the tool is running or not
statusme()
{
  if [ -f $APP_PID_FILE ] ;then
    echo "Checking [${APP_NAME}] status for [pid=${cupid}]..."
    cupid=`cat $APP_PID_FILE`
    if ps -p $cupid>/dev/null ;then
      echo "[${APP_NAME}] process [pid=${cupid}] is running."
    else
      echo "[${APP_NAME}] process [pid=${cupid}] could not be found!"
    fi
  else
    echo "Pid file for [${APP_NAME}] not found, assuming application is not running!"
  fi
}


#shows the usage text for this script
showUsage()
{
  echo "Usage: $0 start | stop | status"
  echo "-start : starts the utility tool. "
  echo "-stop : stops the utility tool."
  echo "-status : display if the tool is running or not."
}


#---------------------
#READ INPUT & VALIDATE
#---------------------
if [ "$#" -lt 1 ] ;then
  showUsage
  exit $RET_CODE_ERR
fi

argument=$1
case ${argument} in
  start)
    startme
    ;;
  stop)
    stopme
    ;;
  status)
    statusme
    ;;
  *)
    echo "Invalind argument specified [${argument}]!"
    showUsage
    exit $RET_CODE_ERR
esac