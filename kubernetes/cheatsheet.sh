# create small clusters
gcloud container clusters create amit-cluster \
  --zone us-central1-a \
  --num-nodes 2 \
  --machine-type e2-small \
  --enable-ip-alias \
  --no-enable-autoscaling \
  --tags=gke-node

# create medium clusters
gcloud container clusters create amit-cluster \
  --zone us-central1-a \
  --num-nodes 3 \
  --machine-type e2-standard-2 \
  --enable-ip-alias \
  --no-enable-autoscaling \
  --tags=gke-node

gcloud container clusters create amit-cluster2 \
  --zone us-east1-b \
  --num-nodes 3 \
  --machine-type e2-standard-2 \
  --enable-ip-alias \
  --no-enable-autoscaling \
  --tags=gke-node

#scale down
gcloud container clusters resize amit-cluster2 \
  --zone us-east1-b \
  --num-nodes 0
#scale up
gcloud container clusters resize amit-cluster \
  --zone us-central1-a \
  --num-nodes 3




# fire wall change for allowing any node port service
gcloud compute firewall-rules create allow-nodeport-service \
  --allow tcp:31233 \
  --target-tags gke-node \
  --source-ranges 0.0.0.0/0 \
  --description "Allow external access to NodePort services"


#clean up
kubectl delete all --all -n default
kubectl delete pvc --all -A
kubectl delete pv --all
kubectl delete -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml
kubectl delete configmap --all -n default
kubectl delete secret --all -n default
kubectl get ns
kubectl delete ns <namespace>

# delete clusters
gcloud container clusters delete amit-cluster --zone us-central1-a
gcloud container clusters delete amit-cluster2 --zone us-east1-b

kubectl apply -f
kubectl delete -f

kubectl get namespaces
kubectl get ns
kubectl get namespaces -o wide
kubectl get namespaces -o yaml

kubectl get all -n amit
kubectl get pods -n amit --watch

kubectl get ingress -n amit #get all in a namespace
kubectl delete namespace amit

kubectl logs discovery-service-deployment-54cbcb7444-dnwvm -n amit
kubectl get events -n amit | grep gateway-service-deployment
#interact with a container
kubectl exec -it -n amit deploy/identity-service-deployment -- /bin/sh

docker run -d -p 8080:8080 \
  -e KC_BOOTSTRAP_ADMIN_USERNAME=admin \
  -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin \
  -e KC_PROXY=edge \
  quay.io/keycloak/keycloak:26.1.4 start-dev

