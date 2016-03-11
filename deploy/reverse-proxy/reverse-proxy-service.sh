#!/bin/bash

case $1 in
    start)
        /bin/bash /usr/local/bin/reverseproxy-start.sh
    ;;
    stop)
        /bin/bash /usr/local/bin/reverseproxy-stop.sh
    ;;
    restart)
        /bin/bash /usr/local/bin/reverseproxy-stop.sh
        /bin/bash /usr/local/bin/reverseproxy-start.sh
    ;;
esac
exit 0
