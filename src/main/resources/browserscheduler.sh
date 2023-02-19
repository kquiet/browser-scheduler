#!/bin/sh

exec java -Dspring.profiles.active=jsonlog -Dchrome_sandbox=no -cp "lib/:lib/*:ext/:ext/*" org.kquiet.browserscheduler.Launcher
