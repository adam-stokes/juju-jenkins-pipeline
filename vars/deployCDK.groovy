/* Deploy CDK
 *
 * @param controller juju controller to use
 * @param model juju model to use
 * @param version_overlay path to bundle overlay for defining k8s versions
 * @param bundle_channel juju charmstore channel to pull bundle from
 */
def call(Map conf) {
    if (!conf.cloud) {
        conf.cloud = 'aws'
    }
    if (!conf.bundle_channel) {
        conf.bundle_channel = 'edge'
    }
    if (!conf.allow_privileged) {
        conf.allow_privileged = false
    }
    sh "juju add-model -c ${conf.controller} ${conf.model} ${conf.cloud}"
    sh "juju deploy -m ${conf.controller}:${conf.model} ${conf.bundle} --overlay ${conf.version_overlay} --channel ${conf.bundle_channel}"
    if (conf.allow_privileged) {
        sh "juju config -m ${conf.controller}:${conf.model} kubernetes-master allow-privileged=true"
        sh "juju config -m ${conf.controller}:${conf.model} kubernetes-worker allow-privileged=true"
    }
    retry(5){
        sh "juju-wait -e ${conf.controller}:${conf.model} -w"
    }
}
