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

# fire wall change for allowing any node port service
gcloud compute firewall-rules create allow-nodeport-service \
  --allow tcp:31233 \
  --target-tags gke-node \
  --source-ranges 0.0.0.0/0 \
  --description "Allow external access to NodePort services"

# delete clusters
gcloud container clusters delete amit-cluster --zone us-central1-a

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