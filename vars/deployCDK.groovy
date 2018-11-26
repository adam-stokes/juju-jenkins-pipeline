/* Deploy CDK
 *
 * @param conf Map
 *    conf.controller juju controller to use
 *    conf.model juju model to use
 *    conf.cloud juju cloud in a cloud/region format
 *    conf.version_overlay path to bundle overlay for defining k8s versions
 *    conf.bundle_channel juju charmstore channel to pull bundle from
 *    conf.allow_privileged enable allow privileged in k8s master/worker
 *    conf.disable_add_model do not run add-model, this is due to factors such
 *                           as localhost that need lxd profiles update prior to deploy
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
    if (!conf.disable_add_model) {
        if (conf.cloud) {
            sh "juju add-model -c ${conf.controller} ${conf.model} ${conf.cloud}"
        } else {
            sh "juju add-model -c ${conf.controller} ${conf.model}"
        }
    }
    sh "juju deploy -m ${conf.controller}:${conf.model} ${conf.bundle} --overlay ${conf.version_overlay} --channel ${conf.bundle_channel}"
    if (conf.allow_privileged) {
        sh "juju config -m ${conf.controller}:${conf.model} kubernetes-master allow-privileged=true"
        sh "juju config -m ${conf.controller}:${conf.model} kubernetes-worker allow-privileged=true"
    }

    sh "juju-wait -e ${conf.controller}:${conf.model} -w"
}
