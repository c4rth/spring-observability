export K6_WEB_DASHBOARD=true
export K6_WEB_DASHBOARD_EXPORT=./html-report.html
export K6_WEB_DASHBOARD_PORT=-1
export K6_WEB_DASHBOARD_OPEN=true
k6 run load-test.js