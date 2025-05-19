#!/bin/sh

APP_USER=${APP_USER:-"root"}

adduser -D -g $APP_USER $APP_USER || true # on relaunching containers, user already exists

exec su "$APP_USER" -s /bin/sh -c "java -jar /app/app.jar"