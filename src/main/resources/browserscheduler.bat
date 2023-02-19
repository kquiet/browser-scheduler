cd %~dp0
start "" javaw -Dspring.profiles.active=jsonlog -Dchrome_sandbox=no -Dwebdriver_headless=yes -cp "lib/;lib/*;ext/;ext/*" org.kquiet.browserscheduler.Launcher
