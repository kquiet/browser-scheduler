cd %~dp0
start "" javaw -Dchrome_sandbox=no -Dwebdriver_headless=yes -cp "lib/;lib/*;ext/;ext/*" org.kquiet.browserscheduler.Launcher
