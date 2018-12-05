/* Deploy Sonobuoy
 *
 * @param controller juju controller to use
 * @param model juju model to use
 * @param version_overlay path to bundle overlay for defining k8s versions
 * @param bundle_channel juju charmstore channel to pull bundle from
 */
def call(String controller,
         String model,
         String version = '0.13.0') {
        sh "wget -qO sonobuoy.tar.gz https://github.com/heptio/sonobuoy/releases/download/v${version}/sonobuoy_${version}_linux_amd64.tar.gz"
        sh "tar xvf sonobuoy.tar.gz"
        sh "mkdir -p $HOME/.kube"
        sh "juju scp -m ${controller}:${model} kubernetes-master/0:config $HOME/.kube/"
        sh "export RBAC_ENABLED=\$(kubectl api-versions | grep \"rbac.authorization.k8s.io/v1beta1\" -c)"
        sh "./sonobuoy run"
}
