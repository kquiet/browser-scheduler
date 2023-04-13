#!/bin/sh

exec java -Dspring.profiles.active=jsonlog -Dchrome_option_args="--no-sandbox,--disable-dev-shm-usage" -cp "lib/:lib/*:ext/:ext/*" org.kquiet.browserscheduler.Launcher
