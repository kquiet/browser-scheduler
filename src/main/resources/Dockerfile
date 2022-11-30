FROM alpine:3.17.0

RUN apk --no-cache add ca-certificates curl jq openjdk17-jre font-noto-cjk 'chromium>105' 'chromium-chromedriver>105' 'firefox>105' \
 && GECKO_URL=`curl -s https://api.github.com/repos/mozilla/geckodriver/releases/latest | jq -r '.assets[]|select(.name|endswith(".tar.gz"))|.browser_download_url|select(contains("linux64"))'` \
 && cd /usr/local/bin && curl -s -L $GECKO_URL | tar -xz && chmod +x geckodriver \
 && mkdir -p /opt/kquiet/job-scheduler/ext \
 && addgroup kquiet && adduser -G kquiet -D kquiet \
 && chown -R kquiet:kquiet /opt/kquiet/

WORKDIR /opt/kquiet/${project.artifactId}

COPY --chown=kquiet:kquiet ["jobscheduler.sh", "./"]

COPY --chown=kquiet:kquiet ["${project.build.finalName}.jar", "lib", "logback.xml", "./lib/"]

USER kquiet:kquiet

ENTRYPOINT ["./jobscheduler.sh"]
