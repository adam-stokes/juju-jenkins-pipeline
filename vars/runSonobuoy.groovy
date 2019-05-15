/* Deploy Sonobuoy
 *
 * @param controller juju controller to use
 * @param model juju model to use
 */
def call(String controller,
         String model) {
    sh "mkdir -p $HOME/.kube"
    sh "juju scp -m ${controller}:${model} kubernetes-master/0:config $HOME/.kube/"
    sh "export RBAC_ENABLED=\$(kubectl api-versions | grep \"rbac.authorization.k8s.io/v1beta1\" -c)"
    sh "/var/lib/jenkins/go/bin/sonobuoy version || true"
    sh "/var/lib/jenkins/go/bin/sonobuoy run --wait"
}
