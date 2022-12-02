#!/bin/sh

exec java -Dchrome_sandbox=no -cp "lib/:lib/*:ext/:ext/*" org.kquiet.browserscheduler.Launcher
