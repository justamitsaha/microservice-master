#All microservices need to register here in the values.yaml file
# add values under additionalScrapeConfigs
cd  kube-prometheus
helm dependencies build
cd ../
helm install prometheus kube-prometheus

cd grafana-loki
helm dependencies build
cd ../
helm install loki grafana-loki


cd grafana-tempo
helm dependencies build
cd ../
helm install tempo grafana-tempo

k get svc
#tempo-grafana-tempo-distributor
#our individual microservices need a tempo url to which my open telemetry is going to send the details of tracing details.
#add this to config   otelExporterEndPoint: http://tempo-grafana-tempo-distributor:4317


cd grafana
helm dependencies build
cd ../
helm install grafana grafana

echo "Browse to http://127.0.0.1:8080"
kubectl port-forward svc/grafana 3000:3000

echo "Password: $(kubectl get secret grafana-admin --namespace default -o jsonpath="{.data.GF_SECURITY_ADMIN_PASSWORD}" | base64 -d)"