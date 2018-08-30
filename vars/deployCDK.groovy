/* Deploy CDK
 *
 * @param controller juju controller to use
 * @param model juju model to use
 * @param version_overlay path to bundle overlay for defining k8s versions
 * @param bundle_channel juju charmstore channel to pull bundle from
 */
def call(String controller,
         String model,
         String bundle,
         String version_overlay,
         String bundle_channel = 'edge',
         Boolean allow_privileged = false) {
    sh "juju add-model -c ${controller} ${model}"
    sh "juju deploy -m ${controller}:${model} ${bundle} --overlay ${version_overlay} --channel ${bundle_channel} --debug"
    if (allow_privileged) {
        sh "juju config -m ${controller}:${model} kubernetes-master allow-privileged=true"
        sh "juju config -m ${controller}:${model} kubernetes-worker allow-privileged=true"
    }
    retry(5){
        sh "juju-wait -e ${controller}:${model} -w"
    }
}
