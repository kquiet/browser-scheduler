# Create a custom Java runtime
#FROM alpine:3.16.2 as jre-build
#RUN apk update && apk --no-cache add binutils openjdk17-jdk \
# && jlink --add-modules java.base,java.desktop,java.logging,java.management,java.datatransfer,java.prefs,java.xml --strip-debug --no-man-pages --no-header-files --compress=2 --output /javaruntime

FROM alpine:3.16.2
#ENV JAVA_HOME=/opt/java/openjdk
#ENV PATH "${JAVA_HOME}/bin:${PATH}"
#COPY --from=jre-build /javaruntime $JAVA_HOME

RUN apk update && apk --no-cache add ca-certificates openjdk17-jre \
 && mkdir -p /usr/share/fonts \
 && wget https://github.com/googlefonts/noto-cjk/raw/main/Sans/SuperOTC/NotoSansCJK.ttc.zip -O /usr/share/fonts/NotoSansCJK.ttc.zip \
 && unzip /usr/share/fonts/NotoSansCJK.ttc.zip -d /usr/share/fonts/ && rm /usr/share/fonts/NotoSansCJK.ttc.zip \
 && mkdir -p /opt/kquiet/job-scheduler/ext \
 && apk --no-cache add 'chromium>100' 'chromium-chromedriver>100' \
 && apk --no-cache add 'firefox>100' \
 && wget https://github.com/mozilla/geckodriver/releases/download/v0.32.0/geckodriver-v0.32.0-linux64.tar.gz -O /opt/kquiet/job-scheduler/geckodriver.tar.gz \
 && tar -zxvf /opt/kquiet/job-scheduler/geckodriver.tar.gz -C /opt/kquiet/job-scheduler/ && rm /opt/kquiet/job-scheduler/geckodriver.tar.gz \
 && addgroup kquiet && adduser -G kquiet -D kquiet

WORKDIR /opt/kquiet/${project.artifactId}

COPY ["jobscheduler.sh", "./"]

COPY ["${project.build.finalName}.jar", "lib", "logback.xml", "./lib/"]

RUN chown -R kquiet:kquiet /opt/kquiet/

USER kquiet:kquiet

ENTRYPOINT ["./jobscheduler.sh"]
